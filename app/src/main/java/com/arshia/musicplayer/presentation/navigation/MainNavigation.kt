package com.arshia.musicplayer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.presentation.mainUI.listScreen.ListScreen
import com.arshia.musicplayer.presentation.mainUI.mainScreen.MainScreen
import com.arshia.musicplayer.presentation.mainUI.mainScreen.MainViewModel
import com.arshia.musicplayer.presentation.mainUI.playerScreen.PlayerScreen
import com.arshia.musicplayer.presentation.settings.SettingsScreen
import kotlin.reflect.typeOf


@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<MainViewModel>()
    NavHost(
        navController = navController,
        startDestination = Routes.MainRoute
    ) {

        composable<Routes.MainRoute> {
            MainScreen(navController, viewModel)
        }

        composable<Routes.SettingRoute> {
            SettingsScreen()
        }

        composable<Routes.PlayerRoute> {
            PlayerScreen(viewModel)
        }

        composable<Routes.AlbumRoute>(
            typeMap = mapOf(
                typeOf<AlbumItem>() to CustomNavType.AlbumItemType
            )
        ) {
            val args = it.toRoute<Routes.AlbumRoute>()
            ListScreen(
                navController,
                viewModel,
                args.albumItem
            )
        }

        composable<Routes.PlaylistRoute>(
            typeMap = mapOf(
                typeOf<PlaylistObject>() to CustomNavType.PlaylistObjectType
            )
        ) {
            val args = it.toRoute<Routes.PlaylistRoute>()
            ListScreen(
                navController,
                viewModel,
                args.playlistObject
            )
        }

    }
}
