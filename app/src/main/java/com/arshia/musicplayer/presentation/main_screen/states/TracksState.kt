package com.arshia.musicplayer.presentation.main_screen.states

import androidx.compose.runtime.Immutable
import com.arshia.musicplayer.data.model.music.TrackItem


@Immutable
data class TracksState(
    val isLoading: Boolean = false,
    val tracksMap: Map<Id, TrackItem> = mutableMapOf(),
    val error: String? = null
)
