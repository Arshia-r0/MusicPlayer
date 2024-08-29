package com.arshia.musicplayer.data.data_source

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import com.arshia.musicplayer.data.model.music.TracksList
import com.arshia.musicplayer.presentation.main_screen.states.AlbumsState
import com.arshia.musicplayer.presentation.main_screen.states.TracksState
import javax.inject.Singleton

typealias Id = Int


@Singleton
class AppDataSource {

    val tracksState = mutableStateOf(TracksState())
    val albumsState = mutableStateOf(AlbumsState())

    var thumbnailsMap = mutableMapOf<Id, Bitmap>()
    var albumsMap = mutableMapOf<Id, TracksList>()

}
