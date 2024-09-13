package com.arshia.musicplayer.presentation.mainUI.listScreen.album

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.musicPlayerService.MusicPlayerController
import com.arshia.musicplayer.presentation.mainUI.appData.AppdataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AlbumListViewModel @Inject constructor(
    val data: AppdataSource,
    val controller: MusicPlayerController
): ViewModel() {

    val selectionMode = mutableStateOf(false)
    val selectTracksMap = mutableStateMapOf<TrackItem, Boolean>()


    fun selectTracks(list: List<TrackItem>) {
        selectionMode.value = true
        list.onEach {
            selectTracksMap += Pair(it, false)
        }
    }

    fun exitSelectMode() {
        selectionMode.value = false
        for((i, j) in selectTracksMap) {
            if(j) selectTracksMap[i] = false
        }
    }

    fun getThumbnail(id: Int): Painter? = data.RetrieveData().getThumbnails(id)

    fun getAlbumTracks(album: AlbumItem): List<TrackItem> = data.RetrieveData().getAlbumTracks(album)
}