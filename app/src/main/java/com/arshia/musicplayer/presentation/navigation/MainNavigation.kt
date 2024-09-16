package com.arshia.musicplayer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.presentation.mainUI.listScreen.album.AlbumListScreen
import com.arshia.musicplayer.presentation.mainUI.listScreen.playlist.PlaylistListScreen
import com.arshia.musicplayer.presentation.mainUI.mainScreen.MainScreen
import com.arshia.musicplayer.presentation.mainUI.playerScreen.PlayerScreen
import com.arshia.musicplayer.presentation.mainUI.selectPlaylistScreen.SelectPlaylistScreen
import com.arshia.musicplayer.presentation.settings.SettingsScreen
import kotlin.reflect.typeOf


@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.MainRoute
    ) {

        composable<Routes.MainRoute> {
            MainScreen(navController)
        }

        composable<Routes.SettingRoute> {
            SettingsScreen(navController)
        }

        composable<Routes.PlayerRoute> {
            PlayerScreen()
        }

        composable<Routes.PlaylistSelectionRoute>(
            typeMap = mapOf(
                typeOf<Set<TrackItem>>() to CustomNavType.TrackItemType
            )
        ) {
            val args = it.toRoute<Routes.PlaylistSelectionRoute>()
            SelectPlaylistScreen(navController, args.tracks)
        }

        composable<Routes.AlbumRoute>(
            typeMap = mapOf(
                typeOf<AlbumItem>() to CustomNavType.AlbumItemType
            )
        ) {
            val args = it.toRoute<Routes.AlbumRoute>()
            AlbumListScreen(
                navController,
                args.albumItem
            )
        }

        composable<Routes.PlaylistRoute>(
            typeMap = mapOf(
                typeOf<PlaylistObject>() to CustomNavType.PlaylistObjectType
            )
        ) {
            val args = it.toRoute<Routes.PlaylistRoute>()
            PlaylistListScreen(
                navController,
                args.playlist
            )
        }

    }
}
