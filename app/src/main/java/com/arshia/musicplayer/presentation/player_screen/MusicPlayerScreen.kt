package com.arshia.musicplayer.presentation.player_screen

import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.arshia.musicplayer.common.convertToText
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerScreen(
    musicPlayerViewModel: MusicPlayerViewModel
) {
    val player = musicPlayerViewModel.player
    val state by musicPlayerViewModel.currentTrack
    val shuffleMode by musicPlayerViewModel.shuffleMode
    val repeatMode by musicPlayerViewModel.musicRepeatMode
    val isPlaying by musicPlayerViewModel.musicIsPlaying
    val transition by musicPlayerViewModel.transition
    var currentPosition by musicPlayerViewModel.currentPosition
    var sliderPosition by musicPlayerViewModel.sliderPosition
    LaunchedEffect(transition) {
        currentPosition = 0
    }
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            currentPosition = player.currentPosition
            delay(1000)
        }
    }
    LaunchedEffect(currentPosition) {
        sliderPosition = player.currentPosition
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
                painter = musicPlayerViewModel.data
                    .thumbnailsMap[state?.albumId]?.let {
                    BitmapPainter(it.asImageBitmap())
                } ?: painterResource(R.drawable.music_icon)
            )
            Spacer(Modifier.height(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = state?.name.toString(),
                    fontSize = 30.sp,
                    maxLines = 1,
                    modifier = Modifier.basicMarquee(Int.MAX_VALUE)
                )
                Text(
                    text = state?.artist.toString(),
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
                        player.seekTo(sliderPosition)
                        currentPosition = sliderPosition
                    },
                    valueRange = 0f..(state?.duration?.toFloat() ?: 1f)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(sliderPosition.convertToText())
                    Text(state?.duration?.convertToText() ?: "")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(if (shuffleMode) R.drawable.shuffle_on else R.drawable.shuffle),
                    contentDescription = "shuffle",
                    modifier = Modifier.clickable { musicPlayerViewModel.toggleShuffle() }

                )
                Icon(
                    painter = painterResource(R.drawable.skip_previous),
                    contentDescription = "next",
                    modifier = Modifier.clickable { musicPlayerViewModel.previousMusic() }
                )
                Icon(
                    painter = painterResource(if (isPlaying) R.drawable.pause else R.drawable.play_arrow),
                    contentDescription = "play",
                    modifier = Modifier.clickable { musicPlayerViewModel.togglePauseMusic() }
                )
                Icon(
                    painter = painterResource(R.drawable.skip_next),
                    contentDescription = "previous",
                    modifier = Modifier.clickable { musicPlayerViewModel.nextMusic() }
                )
                Icon(
                    painter = painterResource(
                        when (repeatMode) {
                            0 -> R.drawable.arrow_right
                            1 -> R.drawable.repeat_one
                            else -> R.drawable.repeat
                        }
                    ),
                    contentDescription = "repeat",
                    modifier = Modifier.clickable { musicPlayerViewModel.toggleRepeatMode() }
                )
            }
        }
    }
}
