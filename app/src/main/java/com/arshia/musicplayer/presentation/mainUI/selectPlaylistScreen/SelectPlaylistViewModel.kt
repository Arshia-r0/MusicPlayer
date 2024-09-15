package com.arshia.musicplayer.presentation.mainUI.selectPlaylistScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.presentation.mainUI.mainData.MainData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SelectPlaylistViewModel @Inject constructor(
    val data: MainData
): ViewModel() {

    val playListsState = data.playlistsState

    fun addToPlaylist(list: Set<TrackItem>, playlistObject: PlaylistObject) {
        viewModelScope.launch {
            data.PlaylistActions().addToPlaylist(list, playlistObject)
            data.RetrieveData().getPlaylists()
        }
    }

}
