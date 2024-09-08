package com.arshia.musicplayer.presentation.main.tabs.components

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
import com.arshia.musicplayer.presentation.main.MainViewModel
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
            IconButton(onClick = { navController.navigate(Routes.SettingRoute) }) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "settings")
            }
            if(tab.selectionMode) {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Create a new playlist")
                }
            }
        }
    )
}
