package com.arshia.musicplayer.data.model



data class TracksList(
    val id: Int = 0,
    val name: String = "AllTracks",
    val items: List<TrackItem> = emptyList()
)
