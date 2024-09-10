package com.arshia.musicplayer.presentation.mainUI.appData

import android.graphics.Bitmap
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.arshia.musicplayer.MusicPlayerApplication
import com.arshia.musicplayer.common.Resource
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.data.repository.music.AlbumsRepository
import com.arshia.musicplayer.data.repository.music.TracksRepository
import com.arshia.musicplayer.data.repository.thumbnail.ThumbnailsRepository
import com.arshia.musicplayer.presentation.mainUI.appData.states.AlbumsState
import com.arshia.musicplayer.presentation.mainUI.appData.states.PlayListsState
import com.arshia.musicplayer.presentation.mainUI.appData.states.TabsState
import com.arshia.musicplayer.presentation.mainUI.appData.states.TracksState
import com.arshia.musicplayer.presentation.mainUI.playerScreen.PlayerState
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

    val playerState = mutableStateOf(PlayerState())

    val tracksState = mutableStateOf(TracksState())
    val albumsState = mutableStateOf(AlbumsState())
    val playlistsState = mutableStateOf(PlayListsState())

    val tab = mutableStateOf(TabsState.Playlists)

    private val playlistDao = MusicPlayerApplication.database.getPlaylistDao()

    private var thumbnailsMap = emptyMap<Id, Bitmap>()
    private val albumsMap = mutableMapOf<Id, List<TrackItem>>()

    private var tracksNotYetInAlbums = mutableListOf<TrackItem>()

    init {
        getData()
    }

    private fun getData() = runBlocking {
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

    private suspend fun getPlaylists() {
        playlistsState.value = PlayListsState(
            playListsMap = playlistDao.getAll().first().associateBy { it.id }
        )
    }

    //

    fun getThumbnails(id: Int): Painter? {
        return thumbnailsMap[id]?.let { BitmapPainter(it.asImageBitmap()) }
    }

    fun getAlbumTracks(album: AlbumItem): List<TrackItem> = albumsMap[album.id] ?: loadTracksInAlbum(album)

    private fun loadTracksInAlbum(album: AlbumItem): List<TrackItem> {
        val iterator = tracksNotYetInAlbums.listIterator()
        val list = mutableListOf<TrackItem>()
        for (i in iterator) {
            if (i.albumId == album.id) {
                list.add(i)
                iterator.remove()
            }
        }
        albumsMap[album.id] = list
        return list.toList()
    }

    suspend fun createPlaylist(name: String) {
        playlistDao.create(
            PlaylistObject(name = name, list = emptyList())
        )
        getPlaylists()
    }

    suspend fun addToPlaylist(list: List<TrackItem>, playlistObject: PlaylistObject) {
        playlistDao.update(
            playlistObject.copy(
                list = playlistObject.list + list
            )
        )
        getPlaylists()
    }

    suspend fun deleteFromPlaylist(list: List<TrackItem>, playlistObject: PlaylistObject) {
        playlistDao.update(
            playlistObject.copy(
                list = playlistObject.list - list.toSet()
            )
        )
        getPlaylists()
    }

    suspend fun changePlaylistName(name: String, playlistObject: PlaylistObject) {
        playlistDao.update(playlistObject.copy(name = name))
        getPlaylists()
    }

    suspend fun deletePlaylist(playlistObjects: List<PlaylistObject>) {
        playlistObjects.forEach {
            playlistDao.deletePlaylist(it.id)
        }
        getPlaylists()
    }

}
