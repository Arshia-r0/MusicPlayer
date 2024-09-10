package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.albums.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.albums.AlbumsViewModel
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.albums.components.AlbumsTopBar
import com.arshia.musicplayer.presentation.mainUI.playerScreen.PlayerBar


@Composable
fun AlbumListScreen(
    navController: NavController,
    album: AlbumItem,
    viewModel: AlbumsViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AlbumsTopBar(navController) }
    ) { ip ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val list = viewModel.getAlbumTracks(album)
                items(list) { track ->
                    AlbumListTrackItem(navController, track, album, viewModel)
                }
            }
            PlayerBar(navController)
        }
    }
}
