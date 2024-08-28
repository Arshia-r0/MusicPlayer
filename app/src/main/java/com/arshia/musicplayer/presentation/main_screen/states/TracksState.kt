package com.arshia.musicplayer.presentation.main_screen.states

import com.arshia.musicplayer.data.model.TracksList


data class TracksState(
    val isLoading: Boolean = false,
    val list: TracksList = TracksList(),
    val error: String? = null
)
