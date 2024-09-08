package com.arshia.musicplayer.presentation.main.screenData

import android.graphics.Bitmap
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import com.arshia.musicplayer.MusicPlayerApplication
import com.arshia.musicplayer.common.Resource
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.repository.music.AlbumsRepository
import com.arshia.musicplayer.data.repository.music.TracksRepository
import com.arshia.musicplayer.data.repository.thumbnail.ThumbnailsRepository
import com.arshia.musicplayer.presentation.main.player.PlayerState
import com.arshia.musicplayer.presentation.main.screenData.states.AlbumsState
import com.arshia.musicplayer.presentation.main.screenData.states.PlayListsState
import com.arshia.musicplayer.presentation.main.screenData.states.TracksState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton


typealias Id = Int


@Singleton
class AppdataSource @Inject constructor(
    private val albumsRepository: AlbumsRepository,
    private val tracksRepository: TracksRepository,
    private val thumbnailsRepository: ThumbnailsRepository,
) {

    val playlistDao = MusicPlayerApplication.database.getPlaylistDao()

    val playerState = mutableStateOf(PlayerState())

    val tracksState = mutableStateOf(TracksState())
    val albumsState = mutableStateOf(AlbumsState())
    val playlistsState = mutableStateOf(PlayListsState())

    var thumbnailsMap = emptyMap<Id, Bitmap>()
    val albumsMap = mutableMapOf<Id, List<TrackItem>>()

    var tracksNotYetInAlbums = mutableListOf<TrackItem>()

    init {
        getData()
    }

    fun getData() = runBlocking {
        getAudios()
        getPlaylists()
        getAlbums()
        getAlbumThumbnails()
    }

    private suspend fun getAudios() {
        tracksRepository.getAll().onEach { result ->
            tracksState.value = when (result) {
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
        }.collect{}
    }

    private suspend fun getAlbums() {
        albumsRepository.getAll().onEach { result ->
            albumsState.value = when (result) {
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
        }.onCompletion { println(albumsState.value) }.collect{}
    }

    suspend fun getPlaylists() {
        playlistsState.value = PlayListsState(
            playListsMap = playlistDao.getAll().first().associateBy { it.id }
        )
    }

    private fun getAlbumThumbnails() {
        if(Build.VERSION.SDK_INT < 29) return
        val map = mutableMapOf<Int, Bitmap>()
        for (album in albumsState.value.albumsMap.values) {
            thumbnailsRepository.getThumbnail(album.uri)?.let {
                map[album.id] = it
            }
        }
        thumbnailsMap = map
    }

}
