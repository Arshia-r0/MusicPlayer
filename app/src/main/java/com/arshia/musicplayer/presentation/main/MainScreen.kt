package com.arshia.musicplayer.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.main.player.PlayerBar
import com.arshia.musicplayer.presentation.main.screenData.states.TabsState
import com.arshia.musicplayer.presentation.main.tabs.albums.AlbumsTab
import com.arshia.musicplayer.presentation.main.tabs.components.BottomBar
import com.arshia.musicplayer.presentation.main.tabs.components.TopBar
import com.arshia.musicplayer.presentation.main.tabs.playlists.PlayListsTab
import com.arshia.musicplayer.presentation.main.tabs.tracks.TracksTab


@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel,
    ) {
    val tab by viewModel.tab
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navController, viewModel) }
    ) { ip ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip)
        ) {
            Surface(modifier = Modifier.weight(1f)) {
                when (tab) {
                    TabsState.Albums -> AlbumsTab(viewModel, navController)
                    TabsState.Playlists -> PlayListsTab(viewModel, navController)
                    TabsState.Tracks -> TracksTab(viewModel, navController)
                }
            }
            PlayerBar(navController, viewModel)
            BottomBar(viewModel)
        }
    }
}
