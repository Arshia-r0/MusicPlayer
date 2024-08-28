package com.arshia.musicplayer.presentation.main_screen.states

import com.arshia.musicplayer.data.model.music.AlbumItem


data class AlbumsState(
    val isLoading: Boolean = false,
    var albumsList: List<AlbumItem> = emptyList<AlbumItem>(),
    val error: String? = null
)
