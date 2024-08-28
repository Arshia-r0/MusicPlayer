package com.arshia.musicplayer.presentation.main_screen.states

import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.music.TracksList


data class TracksState(
    val isLoading: Boolean = false,
    val tracksMap: Map<Int, TrackItem> = mutableMapOf(),
    val error: String? = null
)
