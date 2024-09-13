package com.arshia.musicplayer.presentation.mainUI.mainScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.mainUI.appData.states.TabsState
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.albums.AlbumsTab
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.components.BottomBar
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.PlayListsTab
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.tracks.TracksTab
import com.arshia.musicplayer.presentation.mainUI.playerScreen.PlayerBar


@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
    ) {
    val tab by viewModel.tab
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(top = 0.dp)
    ) { ip ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip)
                .padding(bottom = 20.dp)
        ) {
            Surface(modifier = Modifier.weight(1f)) {
                when (tab) {
                    TabsState.Albums -> AlbumsTab(navController)
                    TabsState.Playlists -> PlayListsTab(navController)
                    TabsState.Tracks -> TracksTab(navController)
                }
            }
            PlayerBar(navController)
            BottomBar(viewModel)
        }
    }
}
