package com.arshia.musicplayer.presentation.main_screen.states

import com.arshia.musicplayer.data.model.music.AlbumItem


data class AlbumsState(
    val isLoading: Boolean = false,
    var albumsMap: Map<Int, AlbumItem> = emptyMap(),
    val error: String? = null
)
