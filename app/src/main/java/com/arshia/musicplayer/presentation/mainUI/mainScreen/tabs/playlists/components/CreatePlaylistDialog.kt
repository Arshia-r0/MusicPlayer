package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.PlaylistsViewModel


@Composable
fun CreatePlaylistDialog(
    viewModel: PlaylistsViewModel,
) {
    var showDialog by viewModel.showCreateDialog
    Dialog(onDismissRequest = { showDialog = false }) {
        var text by remember { mutableStateOf("") }
        Box {
            Column {
                Text("Enter new playlist name: ")
                TextField(
                    value = text,
                    onValueChange = { text = it }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = { showDialog = false },
                    ) {
                        Text("cancel")
                    }
                    Button(
                        onClick = {
                            viewModel.createPlaylist(text)
                            showDialog = false
                        }
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    }
}
