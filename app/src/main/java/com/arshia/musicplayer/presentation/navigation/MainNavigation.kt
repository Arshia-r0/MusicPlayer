package com.arshia.musicplayer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arshia.musicplayer.presentation.mainScreen.MainScreen
import com.arshia.musicplayer.presentation.mainScreen.MainViewModel
import com.arshia.musicplayer.presentation.mainScreen.tabs.albums.components.AlbumScreen
import com.arshia.musicplayer.presentation.playerScreen.MusicPlayerViewModel
import com.arshia.musicplayer.presentation.playerScreen.PlayerScreen
import com.arshia.musicplayer.presentation.settingsScreen.SettingsScreen


@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val mainViewModel = hiltViewModel<MainViewModel>()
    val musicPlayerViewModel = hiltViewModel<MusicPlayerViewModel>()
    NavHost(
        navController = navController,
        startDestination = Routes.MainRoute.route
    ) {
        composable(Routes.MainRoute.route) {
            MainScreen(navController, mainViewModel, musicPlayerViewModel)
        }
        composable(Routes.PlayerRoute.route) {
            PlayerScreen(musicPlayerViewModel)
        }
        composable(Routes.ListRoute.route + "/{listId}") {
            val listId = it.arguments?.getString("listId") ?: "0"
            AlbumScreen(navController, mainViewModel, musicPlayerViewModel, listId)
        }
        composable(Routes.SettingRoute.route) {
            SettingsScreen(navController)
        }
    }
}
