package com.arshia.musicplayer.presentation.main

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.musicplayer.data.dataSource.AppdataSource
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
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

    val tab = mutableStateOf(Tabs.Playlists)

    val playerState = data.playerState
    val tracksState = data.tracksState
    val albumsState = data.albumsState
    val playlistsState = data.playlistsState

    fun getThumbnails(id: Int): Bitmap? = data.thumbnailsMap[id]

    fun getAlbumTracks(album: AlbumItem): List<TrackItem> {
        return data.albumsMap[album.id] ?: loadTracksInAlbum(album)

    }

    fun createPlaylist(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            data.playlistDao.create(
                PlaylistObject(name = name, list = emptyList())
            )
            data.getPlaylists()
        }
    }

    fun addTracksToPlaylist(list: List<TrackItem>, playlistObject: PlaylistObject) {
        viewModelScope.launch(Dispatchers.IO) {
            data.playlistDao.update(playlistObject.copy(
                list = playlistObject.list + list
            ))
        }
    }

    fun deleteTracksFromPlaylist(list: List<TrackItem>, playlistObject: PlaylistObject) {
        viewModelScope.launch(Dispatchers.IO) {
            data.playlistDao.update(playlistObject.copy(
                list = playlistObject.list - list.toSet()
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

    fun refresh() = data.getData()

    private fun loadTracksInAlbum(album: AlbumItem): List<TrackItem> {
        val iterator = data.tracksNotYetInAlbums.listIterator()
        val list = mutableListOf<TrackItem>()
        for (i in iterator) {
            if (i.albumId == album.id) {
                list.add(i)
                iterator.remove()
            }
        }
        data.albumsMap[album.id] = list
        return list.toList()
    }
}
