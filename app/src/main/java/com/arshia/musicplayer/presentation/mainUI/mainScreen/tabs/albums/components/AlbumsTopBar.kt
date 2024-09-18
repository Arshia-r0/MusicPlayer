package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.albums.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsTopBar(
    navScreenController: NavController,
) {
    TopAppBar(
        title = { Text("Albums") },
        actions = {
            IconButton(onClick = { navScreenController.navigate(Routes.SettingRoute) }) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "settings")
            }
        }
    )
}
