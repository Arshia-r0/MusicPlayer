package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.appBars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arshia.musicplayer.presentation.mainUI.mainScreen.MainViewModel
import com.arshia.musicplayer.presentation.mainUI.appData.states.TabsState


@Composable
fun BottomBar(viewModel: MainViewModel) {
    val interactionSource = remember { MutableInteractionSource() }
    var tab by viewModel.tab
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Albums",
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) { tab = TabsState.Albums },
            color = if(tab == TabsState.Albums) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.secondary
        )
        Text(
            text = "Playlists",
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) { tab = TabsState.Playlists },
            color = if(tab == TabsState.Playlists) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.secondary
        )
        Text(
            text = "Tracks",
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) { tab = TabsState.Tracks },
            color = if(tab == TabsState.Tracks) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.secondary
        )
    }
}
