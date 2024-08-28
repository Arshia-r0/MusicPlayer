package com.arshia.musicplayer.presentation.main_screen

import android.graphics.Bitmap
import android.os.Build
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.musicplayer.common.Resource
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.music.TracksList
import com.arshia.musicplayer.data.repository.thumbnail.ThumbnailsRepository
import com.arshia.musicplayer.data.repository.music.AlbumsRepository
import com.arshia.musicplayer.data.repository.music.TracksRepository
import com.arshia.musicplayer.data.data_source.AppDataSource
import com.arshia.musicplayer.presentation.main_screen.states.AlbumsState
import com.arshia.musicplayer.presentation.main_screen.states.TracksState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val albumsRepository: AlbumsRepository,
    private val tracksRepository: TracksRepository,
    private val thumbnailsRepository: ThumbnailsRepository,
    private val savedStateHandle: SavedStateHandle,
    val d: AppDataSource
): ViewModel() {

    var selectedTabIndex = savedStateHandle.getStateFlow("tabIndex", 1)

    private var tracksNotYetInAlbums = mutableListOf<TrackItem>()

    init {
        viewModelScope.launch {
            listOf(
                async { getAudios() },
                async { getAlbums() }
            ).awaitAll()
            getAlbumThumbnails()
        }
    }

    fun changeTab(index: Int) {
        savedStateHandle["tabIndex"] = index
    }

    private suspend fun getAudios() {
        tracksRepository.getAll().onEach { result ->
            d.tracksState.value = when (result) {
                is Resource.Success<List<TrackItem>> -> {
                    TracksState(
                        list = TracksList(
                            items = result.data ?: emptyList()
                        )
                    )
                }
                is Resource.Loading<List<TrackItem>> -> {
                    TracksState(isLoading = true)
                }
                is Resource.Error<List<TrackItem>> -> {
                    TracksState(error = result.message ?: "Error")
                }
            }
        }.onCompletion {
            tracksNotYetInAlbums = d.tracksState.value.list.items.toMutableList()
        }.collect {}
    }

    private suspend fun getAlbums() {
        albumsRepository.getAll().onEach { result ->
            d.albumsState.value = when (result) {
                is Resource.Success<List<AlbumItem>> -> {
                    AlbumsState(albumsList = result.data ?: emptyList())
                }

                is Resource.Loading<List<AlbumItem>> -> {
                    AlbumsState(isLoading = true)
                }

                is Resource.Error<List<AlbumItem>> -> {
                    AlbumsState(error = result.message ?: "Error")
                }
            }
        }.collect {}
    }

    private fun getAlbumThumbnails() {
        if(Build.VERSION.SDK_INT < 29) return
        val map = mutableMapOf<Int, Bitmap>()
        for (album in d.albumsState.value.albumsList) {
            thumbnailsRepository.getThumbnail(album.uri)?.let {
                map[album.id] = it
            }
        }
        d.thumbnailsMap = map
    }

    fun getAlbumTracks(album: AlbumItem): List<TrackItem> {
        d.albumsMap[album.id]?.items?.let {
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
        d.albumsMap[album.id] =
            TracksList(id = album.id, name = album.name, items = result)
        return result.toList()
    }

}
