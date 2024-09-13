package com.arshia.musicplayer.presentation.mainUI.listScreen.playlist.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.presentation.mainUI.listScreen.playlist.PlaylistListViewModel
import com.arshia.musicplayer.presentation.navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistListScreenTopBar(
    navController: NavController,
    viewModel: PlaylistListViewModel,
    playlistObject: PlaylistObject
) {
    var selectionMode by viewModel.selectionMode
    TopAppBar(
        title = { Text("Tracks") },
        actions = {
            if(selectionMode) {
                val containsTrue = { viewModel.selectTracksMap.values.contains(true) }
                val containsFalse = { viewModel.selectTracksMap.values.contains(false) }
                IconButton(
                    onClick = {
                        val set = containsFalse()
                        viewModel.selectTracksMap.onEach {
                            viewModel.selectTracksMap[it.key] = set
                        }
                    }
                ) {
                    Icon(
                        imageVector = if(containsFalse()) {
                            Icons.Outlined.CheckCircle
                        } else Icons.Rounded.CheckCircle,
                        contentDescription = "select all"
                    )
                }
                IconButton(
                    enabled = containsTrue(),
                    onClick = {
                        viewModel.deleteFromPlaylist(
                            viewModel.selectTracksMap.filter { it.value }.keys.toSet(),
                            playlistObject
                        )
                        selectionMode = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete from playlist"
                    )
                }
            } else {
                IconButton(onClick = { navController.navigate(Routes.SettingRoute) }) {
                    Icon(imageVector = Icons.Filled.Settings, contentDescription = "settings")
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                if(selectionMode) viewModel.exitSelectMode()
                else navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "exit select mode"
                )
            }
        }
    )
}