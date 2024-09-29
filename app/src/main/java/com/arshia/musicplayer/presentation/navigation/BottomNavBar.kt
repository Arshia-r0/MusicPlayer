package com.arshia.musicplayer.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.mainUI.mainScreen.components.MainTabs


@Composable
fun BottomNavBar(navBarController: NavController) {
    var selectedTab by remember { mutableStateOf(navBarController.currentDestination?.route) }
    NavigationBar {
        MainTabs.entries.forEach { tab ->
            NavigationBarItem(
                selected = selectedTab == tab.name,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = tab.title
                    )
                },
                label = { Text(tab.title) },
                onClick = {
                    selectedTab = tab.name
                    navBarController.navigate(
                        when(selectedTab) {
                            MainTabs.AlbumsTabRoute.name -> Routes.AlbumsTabRoute
                            MainTabs.PlaylistsTabRoute.name -> Routes.PlaylistsTabRoute
                            MainTabs.TracksTabRoute.name -> Routes.TracksTabRoute
                            else -> {}
                        }
                    )
                }
            )
        }
    }
}
