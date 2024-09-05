package com.arshia.musicplayer.presentation.playerScreen

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.arshia.musicplayer.data.dataSource.AppDataSource
import com.arshia.musicplayer.data.repository.thumbnail.ThumbnailsRepository
import com.arshia.musicplayer.musicPlayerService.MusicPlayerController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val thumbnailsRepository: ThumbnailsRepository,
    val controller: MusicPlayerController,
): ViewModel() {

    val playerState by AppDataSource.playerState

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getThumbnail(uri: String): Bitmap? = thumbnailsRepository.getThumbnail(uri)

}
