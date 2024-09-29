package com.arshia.musicplayer.presentation.mainUI.listScreen.playlist

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
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.presentation.mainUI.listScreen.playlist.components.PlaylistListScreenTopBar
import com.arshia.musicplayer.presentation.mainUI.listScreen.playlist.components.PlaylistListTrackItem
import com.arshia.musicplayer.presentation.mainUI.playerScreen.PlayerBar

@Composable
fun PlaylistListScreen(
    navController: NavController,
    playlistObject: PlaylistObject,
    viewModel: PlaylistListViewModel = hiltViewModel()
) {
    val list = viewModel.currentPlaylistList
    list += playlistObject.list.toList().sortedBy { it.name }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { PlaylistListScreenTopBar(navController, viewModel, playlistObject) },
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
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(items = list) { track ->
                    PlaylistListTrackItem(track, playlistObject)
                }
            }
            PlayerBar(navController)
        }
    }
}
