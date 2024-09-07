package com.arshia.musicplayer.presentation.main.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arshia.musicplayer.R
import com.arshia.musicplayer.common.convertToTime
import com.arshia.musicplayer.presentation.main.MainViewModel
import kotlinx.coroutines.delay


@Composable
fun PlayerScreen(
    viewModel: MainViewModel
) {
    val controller = viewModel.controller.Commands()
    var state by viewModel.playerState
    var sliderPosition by remember { mutableLongStateOf(state.currentPosition) }
    LaunchedEffect(state.isPlaying) {
        while (state.isPlaying) {
            state = state.copy(currentPosition = viewModel.controller.mediaController?.currentPosition ?: 0)
            sliderPosition = state.currentPosition
            delay(1000)
        }
    }
    Scaffold { ip ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip)
                .padding(horizontal = 20.dp, vertical = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .height(200.dp)
                    .clip(RoundedCornerShape(50.dp)),
                contentDescription = "thumbnail",
                painter = state.currentTrack?.albumId?.let { i ->
                    viewModel.getThumbnails(i)?.let {
                        BitmapPainter(it.asImageBitmap())
                    }
                } ?: painterResource(R.drawable.music_icon)
            )
            Spacer(Modifier.height(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = state.currentTrack?.name.toString(),
                    fontSize = 30.sp,
                    maxLines = 1,
                    modifier = Modifier.basicMarquee(Int.MAX_VALUE)
                )
                Text(
                    text = state.currentTrack?.artist.toString(),
                    fontSize = 20.sp,
                    maxLines = 1,
                    modifier = Modifier.basicMarquee(Int.MAX_VALUE)
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Slider(
                    value = sliderPosition.toFloat(),
                    onValueChange = {
                        sliderPosition = it.toLong()
                    },
                    onValueChangeFinished = {
                        controller.seekTo(sliderPosition)
                        state = state.copy(currentPosition = sliderPosition)
                    },
                    valueRange = 0f..(state.currentTrack?.duration?.toFloat() ?: 1f)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(sliderPosition.convertToTime())
                    Text(state.currentTrack?.duration?.convertToTime() ?: "")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(if (state.shuffleMode) R.drawable.shuffle_on else R.drawable.shuffle),
                    contentDescription = "shuffle",
                    modifier = Modifier.clickable { controller.toggleShuffle() }

                )
                Icon(
                    painter = painterResource(R.drawable.skip_previous),
                    contentDescription = "next",
                    modifier = Modifier.clickable { controller.previousMusic() }
                )
                Icon(
                    painter = painterResource(if (state.isPlaying) R.drawable.pause else R.drawable.play_arrow),
                    contentDescription = "play",
                    modifier = Modifier.clickable { controller.togglePauseMusic() }
                )
                Icon(
                    painter = painterResource(R.drawable.skip_next),
                    contentDescription = "previous",
                    modifier = Modifier.clickable { controller.nextMusic() }
                )
                Icon(
                    painter = painterResource(
                        when (state.repeatMode) {
                            0 -> R.drawable.arrow_right
                            1 -> R.drawable.repeat_one
                            else -> R.drawable.repeat
                        }
                    ),
                    contentDescription = "repeat",
                    modifier = Modifier.clickable { controller.toggleRepeatMode() }
                )
            }
        }
    }
}
