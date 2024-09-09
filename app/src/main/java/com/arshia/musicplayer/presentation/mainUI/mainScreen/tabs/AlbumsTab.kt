package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.mainUI.mainScreen.MainViewModel
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.components.AlbumItem


@Composable
fun AlbumsTab(
    viewModel: MainViewModel,
    navController: NavController
) {
    val state by viewModel.albumsState
    if (state.isLoading) {
        Column(
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
                .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            items(state.albumsMap.values.toList()) { album ->
                AlbumItem(
                    navController = navController,
                    viewModel = viewModel,
                    album = album
                )
            }
        }
    }
}
