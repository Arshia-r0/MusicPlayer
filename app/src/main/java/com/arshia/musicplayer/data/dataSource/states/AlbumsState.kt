package com.arshia.musicplayer.data.dataSource.states

import androidx.compose.runtime.Immutable
import com.arshia.musicplayer.data.model.music.AlbumItem


typealias Id = Int

@Immutable
data class AlbumsState(
    val isLoading: Boolean = false,
    var albumsMap: Map<Id, AlbumItem> = emptyMap(),
    val error: String? = null
)
