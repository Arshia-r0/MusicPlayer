package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.components

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.playlists.PlaylistsViewModel
import com.arshia.musicplayer.presentation.navigation.Routes


@Composable
fun PlaylistItem(
    navController: NavController,
    viewModel: PlaylistsViewModel,
    playlist: PlaylistObject
) {
    var isExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                navController.navigate(Routes.PlaylistRoute(playlist))
            },
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = playlist.name,
            fontSize = 20.sp,
            maxLines = 1,
            modifier = Modifier
                .basicMarquee(Int.MAX_VALUE)
                .weight(1f)
                .padding(start = 10.dp)
        )
        Text(
            text = playlist.list.size.toString(),
            fontSize = 15.sp
        )
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
                    text = { Text("change playlist name") },
                    onClick = {
                        isExpanded = false
                        viewModel.showChangeDialog.value = true
                        viewModel.action = { viewModel.changePlaylistName(it, playlist)}
                    }
                )
            }
        }
    }
    if(viewModel.showChangeDialog.value) {
        PlaylistNameDialog(viewModel, "Enter new name:") {
            viewModel.action(it)
        }
    }
}