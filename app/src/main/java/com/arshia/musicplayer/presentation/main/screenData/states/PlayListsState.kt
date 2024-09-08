package com.arshia.musicplayer.presentation.main.screenData.states

import androidx.compose.runtime.Immutable
import com.arshia.musicplayer.data.model.playlist.PlaylistObject


@Immutable
data class PlayListsState(
    val isLoading: Boolean = false,
    val playListsMap: Map<Id, PlaylistObject> = emptyMap(),
    val error: String? = null
)
