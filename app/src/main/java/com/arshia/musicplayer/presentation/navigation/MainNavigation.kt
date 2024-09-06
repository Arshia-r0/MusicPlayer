package com.arshia.musicplayer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arshia.musicplayer.presentation.mainScreen.MainScreen
import com.arshia.musicplayer.presentation.mainScreen.MainViewModel
import com.arshia.musicplayer.presentation.mainScreen.tabs.albums.components.AlbumScreen
import com.arshia.musicplayer.presentation.mainScreen.playerScreen.PlayerScreen
import com.arshia.musicplayer.presentation.settingsScreen.SettingsScreen


@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<MainViewModel>()
    NavHost(
        navController = navController,
        startDestination = Routes.MainRoute.route
    ) {
        composable(Routes.MainRoute.route) {
            MainScreen(navController, viewModel)
        }
        composable(Routes.PlayerRoute.route) {
            PlayerScreen(viewModel)
        }
        composable(Routes.ListRoute.route + "/{listId}") {
            val listId = it.arguments?.getString("listId") ?: "0"
            AlbumScreen(navController, viewModel, listId)
        }
        composable(Routes.SettingRoute.route) {
            SettingsScreen(navController)
        }
    }
}
