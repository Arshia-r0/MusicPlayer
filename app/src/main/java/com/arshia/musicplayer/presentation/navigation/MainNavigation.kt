package com.arshia.musicplayer.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.presentation.mainUI.listScreen.album.AlbumListScreen
import com.arshia.musicplayer.presentation.mainUI.listScreen.playlist.PlaylistListScreen
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.albums.AlbumsTab
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.PlayListsTab
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.tracks.TracksTab
import com.arshia.musicplayer.presentation.mainUI.playerScreen.PlayerBar
import com.arshia.musicplayer.presentation.mainUI.playerScreen.PlayerScreen
import com.arshia.musicplayer.presentation.mainUI.selectPlaylistScreen.SelectPlaylistScreen
import com.arshia.musicplayer.presentation.settings.SettingsScreen
import kotlin.reflect.typeOf


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigation() {
    val navScreenController = rememberNavController()
    NavHost(
        navController = navScreenController,
        startDestination = Routes.MainRoute
    ) {
        composable<Routes.PlayerRoute> {
            PlayerScreen()
        }
        composable<Routes.SettingRoute> {
            SettingsScreen(navScreenController)
        }
        composable<Routes.MainRoute> {
            val navBarController = rememberNavController()
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                contentWindowInsets = WindowInsets(0.dp),
                bottomBar = { BottomNavBar(navBarController) },
            ) { ip ->
                Scaffold(
                    modifier = Modifier.fillMaxSize().padding(ip),
                    contentWindowInsets = WindowInsets(0.dp),
                    bottomBar = { PlayerBar(navScreenController) }
                ) {
                    NavHost(
                        navController = navBarController,
                        startDestination = Routes.PlaylistsTabRoute
                    ) {
                        composable<Routes.PlaylistsTabRoute> {
                            PlayListsTab(navBarController, navScreenController)
                        }
                        composable<Routes.AlbumsTabRoute> {
                            AlbumsTab(navBarController, navScreenController)
                        }
                        composable<Routes.TracksTabRoute> {
                            TracksTab(navBarController, navScreenController)
                        }
                        composable<Routes.AlbumRoute>(
                            typeMap = mapOf(
                                typeOf<AlbumItem>() to CustomNavType.AlbumItemType
                            )
                        ) {
                            val args = it.toRoute<Routes.AlbumRoute>()
                            AlbumListScreen(
                                navBarController,
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
                                navBarController,
                                args.playlist
                            )
                        }
                    }
                }
            }
        }
        composable<Routes.PlaylistSelectionRoute>(
            typeMap = mapOf(
                typeOf<Set<TrackItem>>() to CustomNavType.TrackItemType
            )
        ) {
            val args = it.toRoute<Routes.PlaylistSelectionRoute>()
            SelectPlaylistScreen(navScreenController, args.tracks)
        }
    }
}
