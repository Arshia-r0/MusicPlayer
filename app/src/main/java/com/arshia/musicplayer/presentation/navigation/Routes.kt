package com.arshia.musicplayer.presentation.navigation

import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import kotlinx.serialization.Serializable


@Serializable
sealed class Routes {

    data object MainRoute

    data object SettingRoute

    data object PlayerRoute

    data class AlbumRoute(
        val albumItem: AlbumItem
    )

    data class PlaylistRoute(
        val playlistObject: PlaylistObject
    )

}