package com.arshia.musicplayer.presentation.mainScreen

import android.graphics.Bitmap
import android.os.Build
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.musicplayer.MusicPlayerApplication
import com.arshia.musicplayer.common.Resource
import com.arshia.musicplayer.data.dataSource.AppDataSource
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.music.TracksList
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.data.model.playlist.RoomList
import com.arshia.musicplayer.data.repository.music.AlbumsRepository
import com.arshia.musicplayer.data.repository.music.TracksRepository
import com.arshia.musicplayer.data.repository.thumbnail.ThumbnailsRepository
import com.arshia.musicplayer.presentation.mainScreen.states.AlbumsState
import com.arshia.musicplayer.presentation.mainScreen.states.PlayListsState
import com.arshia.musicplayer.presentation.mainScreen.states.TracksState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val albumsRepository: AlbumsRepository,
    private val tracksRepository: TracksRepository,
    private val thumbnailsRepository: ThumbnailsRepository,
): ViewModel() {

    var selectedTabIndex = mutableIntStateOf(1)
    private var tracksNotYetInAlbums = mutableListOf<TrackItem>()
    private val playlistDao = MusicPlayerApplication.database.getPlaylistDao()

    init {
        viewModelScope.launch {
            getAudios()
            getPlaylists()
            getAlbums()
            getAlbumThumbnails()
        }
    }

    fun changeTab(index: Int) {
        selectedTabIndex.intValue = index
    }

    private suspend fun getAudios() {
        tracksRepository.getAll().onEach { result ->
            AppDataSource.tracksState.value = when (result) {
                is Resource.Success<List<TrackItem>> -> {
                    TracksState(
                        tracksMap = result.data?.associate { it.id to it } ?: emptyMap()
                    ).apply {
                        tracksNotYetInAlbums = result.data?.toMutableList() ?: mutableListOf()
                    }
                }
                is Resource.Loading<List<TrackItem>> -> {
                    TracksState(isLoading = true)
                }
                is Resource.Error<List<TrackItem>> -> {
                    TracksState(error = result.message ?: "Error")
                }
            }
        }.collect()
    }

    private suspend fun getAlbums() {
        albumsRepository.getAll().onEach { result ->
            AppDataSource.albumsState.value = when (result) {
                is Resource.Success<List<AlbumItem>> -> {
                    AlbumsState(
                        albumsMap = result.data?.associate { it.id to it } ?: emptyMap()
                    )
                }

                is Resource.Loading<List<AlbumItem>> -> {
                    AlbumsState(isLoading = true)
                }

                is Resource.Error<List<AlbumItem>> -> {
                    AlbumsState(error = result.message ?: "Error")
                }
            }
        }.collect()
    }

    private suspend fun getPlaylists() {
        AppDataSource.playlistsState.value = PlayListsState(
            playListsMap = playlistDao.getAll().first().associateBy { it.id }
        )
    }

    private fun getAlbumThumbnails() {
        if(Build.VERSION.SDK_INT < 29) return
        val map = mutableMapOf<Int, Bitmap>()
        for (album in AppDataSource.albumsState.value.albumsMap.values) {
            thumbnailsRepository.getThumbnail(album.uri)?.let {
                map[album.id] = it
            }
        }
        AppDataSource.thumbnailsMap = map
    }

    fun getAlbumTracks(album: AlbumItem): List<TrackItem> {
        AppDataSource.albumsMap[album.id]?.items?.let {
            return it
        }
        val result = mutableListOf<TrackItem>()
        val iterator = tracksNotYetInAlbums.listIterator()
        for (i in iterator) {
            if (i.albumId == album.id) {
                result.add(i)
                iterator.remove()
            }
        }
        AppDataSource.albumsMap[album.id] = TracksList(id = album.id, name = album.name, items = result)
        return result.toList()
    }

    fun createPlaylist(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistDao.create(
                PlaylistObject(name = name, tracks = RoomList())
            )
            getPlaylists()
        }
    }

    fun addTracksToPlaylist(list: List<TrackItem>, playlistObject: PlaylistObject) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistDao.update(playlistObject.copy(
                tracks = RoomList(playlistObject.tracks.list + list.map { it.uri })
            ))
        }
    }

    fun deleteTracksFromPlaylist(list: List<TrackItem>, playlistObject: PlaylistObject) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistDao.update(playlistObject.copy(
                tracks = RoomList(playlistObject.tracks.list - list.map { it.uri }.toSet())
            ))
        }
    }

    fun changePlaylistName(name: String, playlistObject: PlaylistObject) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistDao.update(playlistObject.copy(name = name))
            getPlaylists()
        }
    }

    fun deletePlaylist(playlistObjects: List<PlaylistObject>) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistObjects.forEach {
                playlistDao.deletePlaylist(it.id)
            }
            getPlaylists()
        }
    }

}
