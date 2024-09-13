package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.PlaylistsViewModel


@Composable
fun DeletePlaylistDialog(viewModel: PlaylistsViewModel, playlist: PlaylistObject) {
    var showDeleteDialog by viewModel.showDeleteDialog
    Dialog(
        onDismissRequest = { showDeleteDialog = false },
    ) {
        Column {
            Text("Delete playlist?")
            VerticalDivider(
                modifier = Modifier.height(15.dp),
                color = Color.Transparent
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = { showDeleteDialog = false },
                ) {
                    Text("cancel")
                }
                Button(
                    onClick = {
                        viewModel.deletePlaylist(playlist)
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete")
                }
            }
        }
    }
}
