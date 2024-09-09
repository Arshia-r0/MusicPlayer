package com.arshia.musicplayer.presentation.navigation

import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import kotlinx.serialization.Serializable


@Serializable
sealed class Routes {

    @Serializable
    data object MainRoute

    @Serializable
    data object SettingRoute

    @Serializable
    data object PlayerRoute

    @Serializable
    data class TrackSelectionRoute (
        val tracks: List<TrackItem>,
    )

    @Serializable
    data class PlaylistSelectionRoute (
        val playlist: PlaylistObject,
    )

    @Serializable
    data class AlbumRoute(
        val albumItem: AlbumItem
    )

    @Serializable
    data class PlaylistRoute(
        val playlistObject: PlaylistObject
    )

}
