package com.arshia.musicplayer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arshia.musicplayer.presentation.main_screen.MainScreen
import com.arshia.musicplayer.presentation.main_screen.MainViewModel
import com.arshia.musicplayer.presentation.main_screen.tabs.albums.components.AlbumScreen
import com.arshia.musicplayer.presentation.player_screen.MusicPlayerViewModel
import com.arshia.musicplayer.presentation.player_screen.PlayerScreen
import com.arshia.musicplayer.presentation.settings_screen.SettingsScreen


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
