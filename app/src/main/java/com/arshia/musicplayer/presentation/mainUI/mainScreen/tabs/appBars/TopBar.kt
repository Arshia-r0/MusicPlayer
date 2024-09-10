package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.appBars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.mainUI.appData.states.TabsState
import com.arshia.musicplayer.presentation.mainUI.mainScreen.MainViewModel
import com.arshia.musicplayer.presentation.navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    viewModel: MainViewModel
) {
    val tab by viewModel.tab
    TopAppBar(
        title = { Text(tab.title) },
        actions = {
            if(viewModel.selectionMode.value && tab == TabsState.Tracks) {
                IconButton(
                    enabled = true , // disable
                    onClick = {
                        navController.navigate(
                            Routes.PlaylistSelectionRoute(viewModel.selectTracksMap)
                        )
                        viewModel.selectionMode.value = false
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
        }
    )
}
