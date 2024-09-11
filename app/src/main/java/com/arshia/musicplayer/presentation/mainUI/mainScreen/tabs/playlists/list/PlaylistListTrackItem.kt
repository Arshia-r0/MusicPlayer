package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arshia.musicplayer.R
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.PlaylistsViewModel
import com.arshia.musicplayer.presentation.navigation.Routes


@OptIn(ExperimentalFoundationApi::class)
@Stable
@Composable
fun PlaylistListTrackItem(
    navController: NavController,
    track: TrackItem,
    playlist: PlaylistObject,
    viewModel: PlaylistsViewModel = hiltViewModel(),
) {
    val selectionMode by viewModel.selectionMode
    val controller = viewModel.controller.Commands()
    val list = playlist.list.toList()
    val selectedTracksMap = viewModel.selectTracksMap
    var isExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .clip(RoundedCornerShape(8.dp))
            .combinedClickable(
                onClick = {
                    if (selectionMode) {
                        selectedTracksMap[track] = !selectedTracksMap[track]!!
                    } else controller.startMusic(track, list)
                },
                onLongClick = {
                    viewModel.selectTracks(list)
                    viewModel.selectTracksMap[track] = true
                }
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Image(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxSize()
                .clip(RoundedCornerShape(5.dp)),
            contentDescription = "thumbnail",
            painter = viewModel.getThumbnail(track.albumId) ?: painterResource(R.drawable.music_icon)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = track.name,
                fontSize = 20.sp,
                maxLines = 1,
                modifier = Modifier.basicMarquee(Int.MAX_VALUE)
            )
            Text(
                text = track.artist,
                fontSize = 15.sp,
                maxLines = 1,
                modifier = Modifier.basicMarquee(Int.MAX_VALUE)
            )
        }
        if(!viewModel.selectionMode.value) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentSize(Alignment.TopEnd)
            ) {
                IconButton(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = { isExpanded = true }
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "more actions"
                    )
                }
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("add to playlist") },
                        onClick = {
                            navController.navigate(Routes.PlaylistSelectionRoute(setOf(track)))
                        }
                    )
                }
            }
        } else {
            IconButton(onClick = { selectedTracksMap[track] = !selectedTracksMap[track]!! }) {
                if(selectedTracksMap[track] == true) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = "selected")
                } else Icon(imageVector = Icons.Filled.Clear, contentDescription = "not selected")
            }
        }
    }
}
