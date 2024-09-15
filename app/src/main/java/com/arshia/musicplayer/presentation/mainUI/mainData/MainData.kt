package com.arshia.musicplayer.presentation.mainUI.mainData

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
import com.arshia.musicplayer.presentation.mainUI.mainData.states.AlbumsState
import com.arshia.musicplayer.presentation.mainUI.mainData.states.PlayListsState
import com.arshia.musicplayer.presentation.mainUI.mainData.states.TracksState
import com.arshia.musicplayer.presentation.mainUI.mainScreen.components.MainTabs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


typealias Id = Int


@Singleton
class MainData @Inject constructor(
    private val albumsRepository: AlbumsRepository,
    private val tracksRepository: TracksRepository,
    private val thumbnailsRepository: ThumbnailsRepository,
) {

    val tracksState = mutableStateOf(TracksState())
    val albumsState = mutableStateOf(AlbumsState())
    val playlistsState = mutableStateOf(PlayListsState())


    private val playlistDao = MusicPlayerApplication.database.getPlaylistDao()

    private val albumsMap = mutableMapOf<Id, List<TrackItem>>()
    private lateinit var thumbnailsMap: Map<Id, Bitmap>
    private lateinit var tracksNotYetInAlbums: MutableList<TrackItem>

    init {
        RetrieveData().getAll()
    }


    inner class RetrieveData {

        fun getAll() = runBlocking {
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
            }.collect{}
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

        suspend fun getPlaylists() {
            playlistsState.value = PlayListsState(
                playListsMap = playlistDao.getAll().first().associateBy { it.id }
            )
        }

        fun getThumbnails(id: Int): Painter? =
            if(this@MainData::thumbnailsMap.isInitialized) {
                thumbnailsMap[id]?.let { BitmapPainter(it.asImageBitmap()) }
            } else null

        fun getAlbumTracks(album: AlbumItem): List<TrackItem> =
            albumsMap[album.id] ?: loadTracksInAlbum(album)

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

    }

    inner class PlaylistActions {

        suspend fun createPlaylist(name: String) = withContext(Dispatchers.IO) {
            playlistDao.create(
                PlaylistObject(name = name, list = emptySet())
            )
        }

        suspend fun addToPlaylist(list: Set<TrackItem>, playlistObject: PlaylistObject) = withContext(Dispatchers.IO) {
            playlistDao.update(
                playlistObject.copy(list = playlistObject.list + list)
            )
        }

        suspend fun deleteFromPlaylist(list: Set<TrackItem>, playlistObject: PlaylistObject) = withContext(Dispatchers.IO) {
            playlistDao.update(
                playlistObject.copy(
                    list = playlistObject.list - list
                )
            )
        }

        suspend fun changePlaylistName(name: String, playlistObject: PlaylistObject) = withContext(Dispatchers.IO) {
            playlistDao.update(playlistObject.copy(name = name))
        }

        suspend fun deletePlaylist(playlistObjects: Set<PlaylistObject>) = withContext(Dispatchers.IO) {
            playlistObjects.forEach {
                playlistDao.deletePlaylist(it.id)
            }
        }

    }

}
