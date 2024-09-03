package com.arshia.musicplayer.presentation.player_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arshia.musicplayer.R
import com.arshia.musicplayer.presentation.navigation.Routes


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomBar(
    navController: NavController,
    viewModel: MusicPlayerViewModel
) {
    val state = viewModel.playerState
    val controller = viewModel.controller
    BottomAppBar(
        modifier = Modifier
            .clickable { navController.navigate(Routes.PlayerRoute.route) }
            .height(80.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = state.currentTrack?.name.toString(),
                    fontSize = 20.sp,
                    maxLines = 1,
                    modifier = Modifier.basicMarquee(Int.MAX_VALUE)
                )
                Text(
                    text = state.currentTrack?.artist.toString(),
                    fontSize = 15.sp,
                    maxLines = 1,
                    modifier = Modifier.basicMarquee(Int.MAX_VALUE)
                )
            }
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.skip_previous),
                    contentDescription = "next",
                    modifier = Modifier.clickable { controller.previousMusic() }
                )
                Icon(
                    painter = painterResource(if(state.isPlaying) R.drawable.pause else R.drawable.play_arrow),
                    contentDescription = "play",
                    modifier = Modifier.clickable { controller.togglePauseMusic() }
                )
                Icon(
                    painter = painterResource(R.drawable.skip_next),
                    contentDescription = "previous",
                    modifier = Modifier.clickable { controller.nextMusic() }
                )
            }
        }
    }
}
