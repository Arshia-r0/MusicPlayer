package com.arshia.musicplayer.presentation.mainUI.mainScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.mainUI.mainScreen.components.MainTabs
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.albums.AlbumsTab
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.PlayListsTab
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.tracks.TracksTab
import com.arshia.musicplayer.presentation.mainUI.playerScreen.PlayerBar


@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
    ) {
    var selectedTab by viewModel.selectedTab
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
                when (selectedTab) {
                    MainTabs.Albums -> AlbumsTab(navController)
                    MainTabs.Playlists -> PlayListsTab(navController)
                    MainTabs.Tracks -> TracksTab(navController)
                }
            }
            PlayerBar(navController)
            TabRow(
                selectedTabIndex = selectedTab.ordinal,
                indicator = {},
                divider = {}
            ) {
                MainTabs.entries.forEach { tab ->
                    Tab(
                        modifier = Modifier.height(35.dp),
                        selected = selectedTab.ordinal == tab.ordinal,
                        onClick = { selectedTab = tab }
                    ) { Text(tab.name) }
                }
            }
        }
    }
}
