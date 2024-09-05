package com.arshia.musicplayer.data.dataSource

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import com.arshia.musicplayer.data.model.music.TracksList
import com.arshia.musicplayer.presentation.mainScreen.states.AlbumsState
import com.arshia.musicplayer.presentation.mainScreen.states.TracksState
import com.arshia.musicplayer.presentation.playerScreen.PlayerState

typealias Id = Int


object AppDataSource {

    val playerState = mutableStateOf(PlayerState())

    val tracksState = mutableStateOf(TracksState())
    val albumsState = mutableStateOf(AlbumsState())

    var thumbnailsMap = mutableMapOf<Id, Bitmap>()
    var albumsMap = mutableMapOf<Id, TracksList>()

}
