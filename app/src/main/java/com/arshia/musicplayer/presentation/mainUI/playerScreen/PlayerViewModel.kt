package com.arshia.musicplayer.presentation.mainUI.playerScreen

import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import com.arshia.musicplayer.presentation.mainUI.mainData.MainData
import com.arshia.musicplayer.service.MusicPlayerController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PlayerViewModel @Inject constructor(
    val data: MainData,
    val controller: MusicPlayerController
): ViewModel() {

    val playerState = controller.playerState

    fun getThumbnail(id: Int): Painter? = data.RetrieveData().getThumbnails(id)

}
