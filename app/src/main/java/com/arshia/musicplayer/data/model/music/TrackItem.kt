package com.arshia.musicplayer.data.model.music

import kotlinx.serialization.Serializable


@Serializable
data class TrackItem(
    val uri: String,
    val id: Int,
    val name: String,
    val size: Int,
    val artist: String,
    val duration: Long,
    val album: String,
    val albumId: Int,
)
