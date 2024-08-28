package com.arshia.musicplayer.presentation.main_screen.tabs.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arshia.musicplayer.R
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.music.PlayListItem
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.music.TracksList
import com.arshia.musicplayer.presentation.main_screen.MainViewModel
import com.arshia.musicplayer.presentation.player_screen.MusicPlayerViewModel


// tracks tab
@Composable
fun TrackItemRow(
    mainViewModel: MainViewModel,
    musicPlayerViewModel: MusicPlayerViewModel,
    track: TrackItem,
) {
    Content(track, mainViewModel.tracksState.value.list, mainViewModel, musicPlayerViewModel)
}

// albums tab
@Composable
fun TrackItemRow(
    mainViewModel: MainViewModel,
    musicPlayerViewModel: MusicPlayerViewModel,
    track: TrackItem,
    album: AlbumItem
) {
    Content(track, mainViewModel.albumsMap[album.id] ?: TracksList(), mainViewModel, musicPlayerViewModel)
}

// playlists tab
@Composable
fun TrackItemRow(
    mainViewModel: MainViewModel,
    musicPlayerViewModel: MusicPlayerViewModel,
    track: TrackItem,
    playlist: PlayListItem
) {

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Content(
    track: TrackItem,
    list: TracksList,
    mainViewModel: MainViewModel,
    musicPlayerViewModel: MusicPlayerViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                musicPlayerViewModel.startMusic(track, list.items)
            },
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Image(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxSize()
                .clip(RoundedCornerShape(5.dp)),
            contentDescription = "thumbnail",
            painter = mainViewModel.thumbnailsMap[track.albumId]?.let {
                BitmapPainter(it.asImageBitmap())
            } ?: painterResource(R.drawable.music_icon)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = track.name,
                fontSize = 20.sp,
                maxLines = 1,
                modifier = Modifier.basicMarquee(Int.MAX_VALUE)
            )
            Text(
                text = track.artist,
                fontSize = 15.sp,
                maxLines = 1,
                modifier = Modifier.basicMarquee(Int.MAX_VALUE)
            )
        }
    }
}
