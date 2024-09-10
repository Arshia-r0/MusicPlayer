package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.mainUI.mainScreen.MainViewModel
import com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.components.TrackItemRow


@Composable
fun TracksTab(
    viewModel: MainViewModel,
    navController: NavController,
) {
    val state by viewModel.tracksState
    if (state.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
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
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            items(list) { track ->
                TrackItemRow(navController, track, viewModel)
            }
        }
        BackHandler {
            if(viewModel.selectionMode.value) {
                viewModel.selectionMode.value = false
            } else {
                navController.popBackStack()
            }
        }
    }
}
