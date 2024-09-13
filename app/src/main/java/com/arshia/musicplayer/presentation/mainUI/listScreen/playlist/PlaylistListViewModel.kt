package com.arshia.musicplayer.presentation.mainUI.listScreen.playlist

import androidx.compose.runtime.mutableStateListOf
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
class PlaylistListViewModel @Inject constructor(
    val data: AppdataSource,
    val controller: MusicPlayerController
): ViewModel() {

    val currentPlaylistList = mutableStateListOf<TrackItem>()

    val selectionMode = mutableStateOf(false)
    val selectTracksMap = mutableStateMapOf<TrackItem, Boolean>()

    fun selectTracks() {
        selectionMode.value = true
        currentPlaylistList.onEach {
            selectTracksMap += Pair(it, false)
        }
    }

    fun exitSelectMode() {
        selectionMode.value = false
        for((i, j) in selectTracksMap) {
            if(j) selectTracksMap[i] = false
        }
    }

    fun deleteFromPlaylist(list: Set<TrackItem>, playlistObject: PlaylistObject) {
        viewModelScope.launch {
            data.PlaylistAccess().deleteFromPlaylist(list, playlistObject)
            data.RetrieveData().getPlaylists()
            currentPlaylistList -= list
        }
    }

    fun getThumbnail(id: Int): Painter? = data.RetrieveData().getThumbnails(id)

}