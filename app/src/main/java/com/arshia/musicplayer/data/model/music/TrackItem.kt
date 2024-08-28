package com.arshia.musicplayer.data.model.music

import android.net.Uri


data class TrackItem(
    val uri: Uri,
    val id: Int,
    val name: String,
    val size: Int,
    val artist: String,
    val duration: Int,
    val album: String,
    val albumId: Int,
)
