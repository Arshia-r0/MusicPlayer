package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.musicPlayerService.MusicPlayerController
import com.arshia.musicplayer.presentation.mainUI.appData.AppdataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlaylistsViewModel @Inject constructor(
    val data: AppdataSource,
    val controller: MusicPlayerController
): ViewModel() {

    val playListsState = data.playlistsState

    val showCreateDialog = mutableStateOf(false)
    val showChangeDialog = mutableStateOf(false)
    val selectionMode = mutableStateOf(false)
    val selectTracksMap = mutableStateMapOf<TrackItem, Boolean>()
    var action: (String) -> Unit = {}

    fun createPlaylist(name: String) {
        viewModelScope.launch {
            data.PlaylistAccess().createPlaylist(name)
            data.RetrieveData().getPlaylists()
        }
    }

    fun changePlaylistName(name: String, playlistObject: PlaylistObject) {
        viewModelScope.launch {
            data.PlaylistAccess().changePlaylistName(name, playlistObject)
            data.RetrieveData().getPlaylists()
        }
    }

    fun getThumbnail(id: Int): Painter? = data.RetrieveData().getThumbnails(id)

}
