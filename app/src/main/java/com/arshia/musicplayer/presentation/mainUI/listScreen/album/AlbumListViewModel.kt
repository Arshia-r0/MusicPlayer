package com.arshia.musicplayer.presentation.mainUI.listScreen.album

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.presentation.mainUI.mainData.MainData
import com.arshia.musicplayer.service.MusicPlayerController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AlbumListViewModel @Inject constructor(
    val data: MainData,
    val controller: MusicPlayerController
): ViewModel() {

    val selectionMode = mutableStateOf(false)
    var selectTracksMap = mutableStateMapOf<TrackItem, Boolean>()


    fun selectTracks(list: List<TrackItem>) {
        selectionMode.value = true
        list.onEach {
            selectTracksMap += Pair(it, false)
        }
    }

    fun exitSelectMode() {
        selectionMode.value = false
        selectTracksMap = mutableStateMapOf()
    }

    fun getThumbnail(id: Int): Painter? = data.RetrieveData().getThumbnails(id)

    fun getAlbumTracks(album: AlbumItem): List<TrackItem> = data.RetrieveData().getAlbumTracks(album)

}
