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
    data object PlaylistsTabRoute

    @Serializable
    data object TracksTabRoute

    @Serializable
    data object AlbumsTabRoute

    @Serializable
    data object SettingRoute

    @Serializable
    data object PlayerRoute

    @Serializable
    data class PlaylistSelectionRoute (
        val tracks: Set<TrackItem>,
    )

    @Serializable
    data class TrackSelectionRoute (
        val playlist: PlaylistObject,
    )

    @Serializable
    data class AlbumRoute(
        val albumItem: AlbumItem
    )

    @Serializable
    data class PlaylistRoute(
        val playlist: PlaylistObject
    )

}
