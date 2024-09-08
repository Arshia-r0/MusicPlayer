package com.arshia.musicplayer.presentation.main.tabs.playlists.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.presentation.main.MainViewModel
import com.arshia.musicplayer.presentation.main.tabs.components.TrackItemRow


@Composable
fun PlaylistScreen(
    viewModel: MainViewModel,
    playlistObject: PlaylistObject
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(playlistObject.list) { track ->
            TrackItemRow(track, playlistObject.list, viewModel)
        }
    }
}