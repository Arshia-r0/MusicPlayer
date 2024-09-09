package com.arshia.musicplayer.presentation.mainUI.playerScreen

import com.arshia.musicplayer.data.model.music.TrackItem
import kotlinx.serialization.Serializable


@Serializable
data class PlayerState(
    val currentTrack: TrackItem? = null,
    val queue: List<TrackItem> = listOf(),
    val shuffleMode: Boolean = false,
    val repeatMode: Int = 0,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0,
)

