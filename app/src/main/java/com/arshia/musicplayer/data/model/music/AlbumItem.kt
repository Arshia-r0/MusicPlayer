package com.arshia.musicplayer.data.model.music

import kotlinx.serialization.Serializable


@Serializable
data class AlbumItem(
    val uri: String,
    val id: Int,
    val name: String,
)
