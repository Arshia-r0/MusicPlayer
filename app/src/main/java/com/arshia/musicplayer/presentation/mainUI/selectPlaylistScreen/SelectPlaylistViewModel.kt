package com.arshia.musicplayer.presentation.mainUI.selectPlaylistScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.presentation.mainUI.appData.AppdataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SelectPlaylistViewModel @Inject constructor(
    val data: AppdataSource
): ViewModel() {

    val playListsState = data.playlistsState

    fun addToPlaylist(list: List<TrackItem>, playlistObject: PlaylistObject) {
        viewModelScope.launch {
            data.addToPlaylist(list, playlistObject)
        }
    }

}
