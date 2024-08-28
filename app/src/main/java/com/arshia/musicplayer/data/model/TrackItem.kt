package com.arshia.musicplayer.data.model

import android.net.Uri
import androidx.media3.common.MediaItem


data class TrackItem(
    val uri: Uri,
    val id: Int,
    val name: String,
    val size: Int,
    val artist: String,
    val duration: Int,
    val album: String,
    val albumId: Int,
    val mediaItem: MediaItem
)
