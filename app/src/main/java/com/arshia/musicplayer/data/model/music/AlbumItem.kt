package com.arshia.musicplayer.data.model.music

import android.net.Uri


data class AlbumItem(
    val uri: Uri,
    val id: Int,
    val name: String
)
