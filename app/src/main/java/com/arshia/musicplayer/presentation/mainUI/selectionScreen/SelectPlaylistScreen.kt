package com.arshia.musicplayer.presentation.mainUI.selectionScreen

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.presentation.mainUI.mainScreen.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPlaylistScreen(
    viewModel: MainViewModel,
    navController: NavController,
    tracks: List<TrackItem>
) {
    val state by viewModel.playlistsState
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Select playlist") }) }
    ) { ip ->
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
                        .clickable {
                            viewModel.addToPlaylist(tracks, playlist)
                            navController.navigateUp()
                        },
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
    }
}