package com.arshia.musicplayer.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.main.player.PlayerBar
import com.arshia.musicplayer.presentation.main.tabs.albums.AlbumsTab
import com.arshia.musicplayer.presentation.main.tabs.components.TopBar
import com.arshia.musicplayer.presentation.main.tabs.playlists.PlayListsTab
import com.arshia.musicplayer.presentation.main.tabs.tracks.TracksTab
import com.arshia.musicplayer.presentation.navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel,
    ) {
    val tab by viewModel.tab
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {  },
        topBar = { TopBar(
            title = "Rumbar",
            actions = {
                IconButton(onClick = { navController.navigate(Routes.SettingRoute) }) {
                    Icon(imageVector = Icons.Filled.Settings, contentDescription = "settings")
                }
            }
        ) }
    ) { ip ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip)
        ) {
            Surface(modifier = Modifier.weight(1f)) {
                when (tab) {
                    Tabs.Albums -> AlbumsTab(viewModel, navController)
                    Tabs.Playlists -> PlayListsTab(viewModel, navController)
                    Tabs.Tracks -> TracksTab(viewModel)
                }
            }
            PlayerBar(navController, viewModel)
            BottomBar(viewModel)
        }
    }
}

@Composable
fun BottomBar(viewModel: MainViewModel) {
    val interactionSource = remember { MutableInteractionSource() }
    var tab by viewModel.tab
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Albums",
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) { tab = Tabs.Albums },
            color = if(tab == Tabs.Albums) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.secondary
        )
        Text(
            text = "Playlists",
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) { tab = Tabs.Playlists },
            color = if(tab == Tabs.Playlists) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.secondary
        )
        Text(
            text = "Tracks",
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) { tab = Tabs.Tracks },
            color = if(tab == Tabs.Tracks) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.secondary
        )
    }
}

enum class Tabs {
    Albums, Playlists, Tracks
}
