package com.arshia.musicplayer.presentation.mainUI.listScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.presentation.mainUI.mainScreen.MainViewModel
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.appBars.TopBar
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.components.TrackItemRow


@Composable
fun ListScreen(
    navController: NavController,
    viewModel: MainViewModel,
    album: AlbumItem
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navController, viewModel) }
    ) { ip ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val list = viewModel.getAlbumTracks(album)
            items(list) { track ->
                TrackItemRow(navController, track, list, viewModel)
            }
        }
    }
}

@Composable
fun ListScreen(
    navController: NavController,
    viewModel: MainViewModel,
    playlistObject: PlaylistObject,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navController, viewModel) }
    ) { ip ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(playlistObject.list) { track ->
                TrackItemRow(navController, track, playlistObject.list, viewModel)
            }
        }
    }
}
