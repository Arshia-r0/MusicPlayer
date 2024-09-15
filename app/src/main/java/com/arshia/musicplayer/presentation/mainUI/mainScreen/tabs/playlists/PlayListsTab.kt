package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.components.ChangePlaylistNameDialog
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.components.CreatePlaylistDialog
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.components.DeletePlaylistDialog
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.components.PlaylistItem
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.components.PlaylistsTopBar


@Composable
fun PlayListsTab(
    navController: NavController,
    viewModel: PlaylistsViewModel = hiltViewModel()
) {
    val state by viewModel.playListsState
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { PlaylistsTopBar(navController, viewModel) }
    ) { ip ->
        if (state.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(ip),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { CircularProgressIndicator() }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(ip)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.playListsMap.values.toList()) { playlist ->
                    PlaylistItem(navController, viewModel, playlist)
                }
            }
            if (viewModel.showCreateDialog.value) {
                CreatePlaylistDialog(viewModel)
            }
            if (viewModel.showDeleteDialog.value) {
                DeletePlaylistDialog(viewModel)
            }
            if (viewModel.showChangeDialog.value) {
                ChangePlaylistNameDialog(viewModel)
            }
        }
    }
}
