package com.arshia.musicplayer.presentation.mainScreen

import android.graphics.Bitmap
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.musicplayer.data.dataSource.AppdataSource
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.music.TracksList
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.data.model.playlist.RoomList
import com.arshia.musicplayer.musicPlayerService.MusicPlayerController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    val data: AppdataSource,
    val controller: MusicPlayerController
): ViewModel() {

    var selectedTabIndex = mutableIntStateOf(1)

    val playerState = data.playerState
    val tracksState = data.tracksState
    val albumsState = data.albumsState
    val playlistsState = data.playlistsState

    fun getThumbnails(id: Int): Bitmap? = data.thumbnailsMap[id]

    fun changeTab(index: Int) {
        selectedTabIndex.intValue = index
    }

    fun getAlbumTracks(album: AlbumItem): List<TrackItem> {
        data.albumsMap[album.id]?.items?.let {
            return it
        }
        val result = mutableListOf<TrackItem>()
        val iterator = data.tracksNotYetInAlbums.listIterator()
        for (i in iterator) {
            if (i.albumId == album.id) {
                result.add(i)
                iterator.remove()
            }
        }
        data.albumsMap[album.id] = TracksList(id = album.id, name = album.name, items = result)
        return result.toList()
    }

    fun createPlaylist(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            data.playlistDao.create(
                PlaylistObject(name = name, tracks = RoomList())
            )
            data.getPlaylists()
        }
    }

    fun addTracksToPlaylist(list: List<TrackItem>, playlistObject: PlaylistObject) {
        viewModelScope.launch(Dispatchers.IO) {
            data.playlistDao.update(playlistObject.copy(
                tracks = RoomList(playlistObject.tracks.list + list.map { it.uri })
            ))
        }
    }

    fun deleteTracksFromPlaylist(list: List<TrackItem>, playlistObject: PlaylistObject) {
        viewModelScope.launch(Dispatchers.IO) {
            data.playlistDao.update(playlistObject.copy(
                tracks = RoomList(playlistObject.tracks.list - list.map { it.uri }.toSet())
            ))
        }
    }

    fun changePlaylistName(name: String, playlistObject: PlaylistObject) {
        viewModelScope.launch(Dispatchers.IO) {
            data.playlistDao.update(playlistObject.copy(name = name))
            data.getPlaylists()
        }
    }

    fun deletePlaylist(playlistObjects: List<PlaylistObject>) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistObjects.forEach {
                data.playlistDao.deletePlaylist(it.id)
            }
            data.getPlaylists()
        }
    }

}
