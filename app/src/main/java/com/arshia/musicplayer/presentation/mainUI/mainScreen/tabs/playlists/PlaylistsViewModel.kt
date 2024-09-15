package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.presentation.mainUI.mainData.MainData
import com.arshia.musicplayer.service.MusicPlayerController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlaylistsViewModel @Inject constructor(
    val data: MainData,
    val controller: MusicPlayerController
): ViewModel() {

    val playListsState = data.playlistsState

    val showCreateDialog = mutableStateOf(false)
    val showChangeDialog = mutableStateOf(false)
    val showDeleteDialog = mutableStateOf(false)


    var playlist: PlaylistObject? = null
    val selectionMode = mutableStateOf(false)
    val selectTracksMap = mutableStateMapOf<TrackItem, Boolean>()

    fun createPlaylist(name: String) {
        viewModelScope.launch {
            data.PlaylistActions().createPlaylist(name)
            refreshPlaylists()
        }
    }

    fun changePlaylistName(name: String, playlistObject: PlaylistObject) {
        viewModelScope.launch {
            data.PlaylistActions().changePlaylistName(name, playlistObject)
            refreshPlaylists()
        }
    }

    fun deletePlaylist(playlistObject: PlaylistObject) {
        viewModelScope.launch {
            data.PlaylistActions().deletePlaylist(setOf(playlistObject))
            refreshPlaylists()
        }
    }

    fun getThumbnail(id: Int): Painter? = data.RetrieveData().getThumbnails(id)

    private suspend fun refreshPlaylists() = data.RetrieveData().getPlaylists()

}
