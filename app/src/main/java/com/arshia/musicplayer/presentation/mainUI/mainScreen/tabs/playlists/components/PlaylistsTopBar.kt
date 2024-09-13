package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.PlaylistsViewModel
import com.arshia.musicplayer.presentation.navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsTopBar(
    navController: NavController,
    viewModel: PlaylistsViewModel
) {
    TopAppBar(
        title = { Text("Playlists") },
        actions = {
            IconButton(onClick = {
                viewModel.showCreateDialog.value = true
                viewModel.action = { viewModel.createPlaylist(it) }
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Create a new playlist"
                )
            }
            IconButton(onClick = { navController.navigate(Routes.SettingRoute) }) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "settings")
            }
        }
    )
}
