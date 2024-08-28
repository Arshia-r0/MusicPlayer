package com.arshia.musicplayer.presentation.main_screen

import android.graphics.Bitmap
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.musicplayer.common.Resource
import com.arshia.musicplayer.data.data_source.AppDataSource
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.music.TracksList
import com.arshia.musicplayer.data.repository.music.AlbumsRepository
import com.arshia.musicplayer.data.repository.music.TracksRepository
import com.arshia.musicplayer.data.repository.thumbnail.ThumbnailsRepository
import com.arshia.musicplayer.presentation.main_screen.states.AlbumsState
import com.arshia.musicplayer.presentation.main_screen.states.TracksState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val albumsRepository: AlbumsRepository,
    private val tracksRepository: TracksRepository,
    private val thumbnailsRepository: ThumbnailsRepository,
    val data: AppDataSource
): ViewModel() {

    var selectedTabIndex = mutableStateOf(1)

    private var tracksNotYetInAlbums = mutableListOf<TrackItem>()

    init {
        viewModelScope.launch {
            getAudios()
            getAlbums()
            getAlbumThumbnails()
        }
    }

    fun changeTab(index: Int) {
        selectedTabIndex.value = index
    }

    private suspend fun getAudios() {
        tracksRepository.getAll().onEach { result ->
            data.tracksState.value = when (result) {
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
            data.albumsState.value = when (result) {
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

    private fun getAlbumThumbnails() {
        if(Build.VERSION.SDK_INT < 29) return
        val map = mutableMapOf<Int, Bitmap>()
        for (album in data.albumsState.value.albumsMap.values) {
            thumbnailsRepository.getThumbnail(album.uri)?.let {
                map[album.id] = it
            }
        }
        data.thumbnailsMap = map
    }

    fun getAlbumTracks(album: AlbumItem): List<TrackItem> {
        data.albumsMap[album.id]?.items?.let {
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
        data.albumsMap[album.id] = TracksList(id = album.id, name = album.name, items = result)
        return result.toList()
    }

}
