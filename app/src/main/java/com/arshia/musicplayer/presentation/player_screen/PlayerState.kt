package com.arshia.musicplayer.presentation.player_screen

import com.arshia.musicplayer.data.model.music.TrackItem


data class PlayerState(
    val currentTrack: TrackItem? = null,
    val shuffleMode: Boolean = false,
    val repeatMode: Int = 0,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0,
)
