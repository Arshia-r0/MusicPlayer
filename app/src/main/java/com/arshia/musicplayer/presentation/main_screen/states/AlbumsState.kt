package com.arshia.musicplayer.presentation.main_screen.states

import com.arshia.musicplayer.data.model.music.AlbumItem


typealias Id = Int

data class AlbumsState(
    val isLoading: Boolean = false,
    var albumsMap: Map<Id, AlbumItem> = emptyMap(),
    val error: String? = null
)
