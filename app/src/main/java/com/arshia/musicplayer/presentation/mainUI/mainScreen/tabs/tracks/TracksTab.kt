package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.tracks

import androidx.activity.compose.BackHandler
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
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.tracks.components.TrackItem
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.tracks.components.TracksTopBar


@Composable
fun TracksTab(
    navController: NavController,
    viewModel: TracksViewModel = hiltViewModel(),
) {
    val state by viewModel.tracksState
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TracksTopBar(navController, viewModel) }
    ) { ip ->
        if (state.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(ip),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            val list = state.tracksMap.values.toList()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(ip)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                items(list) { track ->
                    TrackItem(navController, track, viewModel)
                }
            }
            BackHandler {
                if (viewModel.selectionMode.value) {
                    viewModel.selectionMode.value = false
                } else {
                    navController.popBackStack()
                }
            }
        }
    }
}
