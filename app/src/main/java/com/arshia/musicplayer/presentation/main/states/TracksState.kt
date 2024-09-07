package com.arshia.musicplayer.presentation.main.states

import androidx.compose.runtime.Immutable
import com.arshia.musicplayer.data.model.music.TrackItem


@Immutable
data class TracksState(
    val isLoading: Boolean = false,
    val tracksMap: Map<Id, TrackItem> = emptyMap(),
    val error: String? = null
)
