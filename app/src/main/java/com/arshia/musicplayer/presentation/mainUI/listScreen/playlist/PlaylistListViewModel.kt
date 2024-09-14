package com.arshia.musicplayer.presentation.mainUI.listScreen.playlist

import androidx.compose.runtime.mutableStateListOf
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
class PlaylistListViewModel @Inject constructor(
    val data: MainData,
    val controller: MusicPlayerController
): ViewModel() {

    var currentPlaylistList = mutableStateListOf<TrackItem>()

    val selectionMode = mutableStateOf(false)
    var selectTracksMap = mutableStateMapOf<TrackItem, Boolean>()

    fun selectTracks() {
        selectionMode.value = true
        currentPlaylistList.onEach {
            selectTracksMap += Pair(it, false)
        }
    }

    fun exitSelectMode() {
        selectionMode.value = false
        selectTracksMap = mutableStateMapOf()
    }

    fun deleteFromPlaylist(list: Set<TrackItem>, playlistObject: PlaylistObject) {
        viewModelScope.launch {
            data.PlaylistActions().deleteFromPlaylist(list, playlistObject)
            refreshPlaylists()
            currentPlaylistList -= list
        }
    }

    fun getThumbnail(id: Int): Painter? = data.RetrieveData().getThumbnails(id)

    private suspend fun refreshPlaylists() = data.RetrieveData().getPlaylists()

}
