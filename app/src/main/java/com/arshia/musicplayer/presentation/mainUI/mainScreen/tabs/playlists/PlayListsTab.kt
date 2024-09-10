package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.components.PlaylistsTopBar
import com.arshia.musicplayer.presentation.navigation.Routes


@Composable
fun PlayListsTab(
    navController: NavController,
    viewModel: PlaylistsViewModel = hiltViewModel()
) {
    val state by viewModel.playListsState
    var showDialog by viewModel.showDialog
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(65.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { navController.navigate(Routes.PlaylistRoute(playlist)) },
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = playlist.name,
                            fontSize = 20.sp,
                            maxLines = 1,
                            modifier = Modifier.basicMarquee(Int.MAX_VALUE)
                        )
                        Text(
                            text = playlist.list.size.toString(),
                            fontSize = 15.sp
                        )
                    }
                }
            }
            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    var text by remember { mutableStateOf("") }
                    Column {
                        Text("Create a new playlist")
                        TextField(value = text, onValueChange = { text = it })
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
                                    TODO("navigate to selection screen")
                                }
                            ) {
                                Text("create playlist")
                            }
                        }
                    }
                }
            }
        }
    }
}
