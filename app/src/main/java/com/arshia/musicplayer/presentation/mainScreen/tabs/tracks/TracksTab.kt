package com.arshia.musicplayer.presentation.mainScreen.tabs.tracks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arshia.musicplayer.data.dataSource.AppDataSource
import com.arshia.musicplayer.presentation.mainScreen.MainViewModel
import com.arshia.musicplayer.presentation.mainScreen.tabs.components.TrackItemRow
import com.arshia.musicplayer.presentation.playerScreen.MusicPlayerViewModel


@Composable
fun TracksTab(
    mainViewModel: MainViewModel,
    musicPlayerViewModel: MusicPlayerViewModel
) {
    val state = AppDataSource.tracksState
    if (state.value.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.value.tracksMap.values.toList()) { track ->
                TrackItemRow(mainViewModel, musicPlayerViewModel, track)
            }
        }
    }
}
