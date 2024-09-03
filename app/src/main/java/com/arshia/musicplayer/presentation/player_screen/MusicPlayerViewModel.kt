package com.arshia.musicplayer.presentation.player_screen

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.arshia.musicplayer.data.repository.thumbnail.ThumbnailsRepository
import com.arshia.musicplayer.music_player_service.MusicPlayerController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val thumbnailsRepository: ThumbnailsRepository,
    val controller: MusicPlayerController
): ViewModel() {

    val playerState by this.controller.playerState


    fun getThumbnail(uri: Uri): Bitmap? = thumbnailsRepository.getThumbnail(uri)

}
