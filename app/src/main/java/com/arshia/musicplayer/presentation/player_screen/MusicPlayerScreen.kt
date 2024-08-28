package com.arshia.musicplayer.presentation.player_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arshia.musicplayer.R


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerScreen(
    musicPlayerViewModel: MusicPlayerViewModel
) {
    val state by musicPlayerViewModel.currentTrack
    val shuffleMode by musicPlayerViewModel.shuffleMode
    val repeatMode by musicPlayerViewModel.musicRepeatMode
    val isPlaying by musicPlayerViewModel.musicIsPlaying
    Scaffold { ip ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip)
                .padding(horizontal = 25.dp, vertical = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentDescription = "thumbnail",
                painter = painterResource(R.drawable.music_icon)
            )
            Text(
                text = state.mediaMetadata.title.toString(),
                fontSize = 30.sp,
                maxLines = 1,
                modifier = Modifier.basicMarquee(Int.MAX_VALUE)
            )
            Text(
                text = state.mediaMetadata.artist.toString(),
                fontSize = 20.sp,
                maxLines = 1,
                modifier = Modifier.basicMarquee(Int.MAX_VALUE)
            )

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
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
            }
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(if (shuffleMode) R.drawable.shuffle_on else R.drawable.shuffle),
                    contentDescription = "shuffle",
                    modifier = Modifier.clickable { musicPlayerViewModel.toggleShuffle() }

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
