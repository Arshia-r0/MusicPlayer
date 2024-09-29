package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.albums

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.albums.components.AlbumItem
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.albums.components.AlbumsTopBar


@Composable
fun AlbumsTab(
    navBarController: NavController,
    navScreenController: NavController,
    viewModel: AlbumsViewModel = hiltViewModel()
) {
    val state by viewModel.albumsState
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AlbumsTopBar(navScreenController) },
    ) { ip ->
        if (state.isLoading) {
            Column(
                modifier = Modifier
                    .padding(ip)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(ip)
                    .padding(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                items(state.albumsMap.values.toList()) { album ->
                    AlbumItem(
                        navController = navBarController,
                        viewModel = viewModel,
                        album = album
                    )
                }
            }
        }
    }
}
