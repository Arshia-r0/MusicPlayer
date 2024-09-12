package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.tracks.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.tracks.TracksViewModel
import com.arshia.musicplayer.presentation.navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TracksTopBar(
    navController: NavController,
    viewModel: TracksViewModel
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
                        navController.navigate(
                            Routes.PlaylistSelectionRoute(viewModel.selectTracksMap.filter { it.value }.keys)
                        )
                        selectionMode = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "add to playlist"
                    )
                }
            } else {
                IconButton(onClick = { navController.navigate(Routes.SettingRoute) }) {
                    Icon(imageVector = Icons.Filled.Settings, contentDescription = "settings")
                }
            }
        },
        navigationIcon = {
            if(selectionMode) {
                IconButton(onClick = { viewModel.exitSelectMode()}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "exit select mode"
                    )
                }
            }
        }
    )
}
