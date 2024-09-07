package com.arshia.musicplayer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.arshia.musicplayer.presentation.main.MainScreen
import com.arshia.musicplayer.presentation.main.MainViewModel
import com.arshia.musicplayer.presentation.main.player.PlayerScreen
import com.arshia.musicplayer.presentation.main.tabs.albums.components.AlbumsScreen
import com.arshia.musicplayer.presentation.settings.SettingsScreen


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
        composable<Routes.PlayerRoute> {
            PlayerScreen(viewModel)
        }
        composable<Routes.AlbumRoute> {
            val args = it.toRoute<Routes.AlbumRoute>()
            AlbumsScreen(
                navController,
                viewModel,
                args.albumItem
            )
        }
        composable<Routes.SettingRoute> {
            SettingsScreen(navController)
        }
    }
}
