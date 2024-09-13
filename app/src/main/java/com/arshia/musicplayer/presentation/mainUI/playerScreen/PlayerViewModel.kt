package com.arshia.musicplayer.presentation.mainUI.playerScreen

import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import com.arshia.musicplayer.musicPlayerService.MusicPlayerController
import com.arshia.musicplayer.presentation.mainUI.appData.AppdataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PlayerViewModel @Inject constructor(
    val data: AppdataSource,
    val controller: MusicPlayerController
): ViewModel() {

    val playerState = data.playerState

    fun getThumbnail(id: Int): Painter? = data.RetrieveData().getThumbnails(id)

}
