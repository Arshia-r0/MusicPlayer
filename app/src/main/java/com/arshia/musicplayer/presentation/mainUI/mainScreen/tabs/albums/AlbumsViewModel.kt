package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.albums

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.presentation.mainUI.mainData.MainData
import com.arshia.musicplayer.service.MusicPlayerController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AlbumsViewModel @Inject constructor(
    val data: MainData,
    val controller: MusicPlayerController
): ViewModel() {

    val albumsState = data.albumsState

    val selectionMode = mutableStateOf(false)
    val selectTracksMap = mutableStateMapOf<TrackItem, Boolean>()

    fun getThumbnail(id: Int): Painter? = data.RetrieveData().getThumbnails(id)

}
