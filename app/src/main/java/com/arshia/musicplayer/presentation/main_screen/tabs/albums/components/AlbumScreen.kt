package com.arshia.musicplayer.presentation.main_screen.tabs.albums.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.main_screen.MainViewModel
import com.arshia.musicplayer.presentation.main_screen.tabs.components.TopBar
import com.arshia.musicplayer.presentation.main_screen.tabs.components.TrackItemRow
import com.arshia.musicplayer.presentation.player_screen.BottomBar
import com.arshia.musicplayer.presentation.player_screen.MusicPlayerViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    musicPlayerViewModel: MusicPlayerViewModel,
    listId: String
) {
    val id = listId.toInt()
    val album = mainViewModel.data.albumsState.value.albumsMap[id] ?: return
    val list = mainViewModel.getAlbumTracks(album)
    Scaffold(
        topBar = { TopBar(
            title = album.name,
            canNavigateBack = true,
            navController = navController
        ) },
        bottomBar = { BottomBar(navController, musicPlayerViewModel) },
        modifier = Modifier.fillMaxSize()
    ) { ip ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(list) { track ->
                TrackItemRow(mainViewModel, musicPlayerViewModel, track, album)
            }
        }
    }
}
