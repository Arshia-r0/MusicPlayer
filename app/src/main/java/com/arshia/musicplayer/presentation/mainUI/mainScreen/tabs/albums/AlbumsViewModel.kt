package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.albums

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.musicPlayerService.MusicPlayerController
import com.arshia.musicplayer.presentation.mainUI.appData.AppdataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AlbumsViewModel @Inject constructor(
    val data: AppdataSource,
    val controller: MusicPlayerController
): ViewModel() {

    val albumsState = data.albumsState

    val selectionMode = mutableStateOf(false)
    val selectTracksMap = mutableStateMapOf<TrackItem, Boolean>()

    fun getThumbnail(id: Int): Painter? = data.RetrieveData().getThumbnails(id)

}
