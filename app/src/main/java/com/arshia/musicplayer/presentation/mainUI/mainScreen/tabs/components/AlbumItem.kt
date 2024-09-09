package com.arshia.musicplayer.presentation.mainUI.mainScreen.tabs.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arshia.musicplayer.R
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.presentation.mainUI.mainScreen.MainViewModel
import com.arshia.musicplayer.presentation.navigation.Routes


@Stable
@Composable
fun AlbumItem(
    viewModel: MainViewModel,
    navController: NavController,
    album: AlbumItem,
) {
    Box{
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .clickable {
                        navController.navigate(
                            Routes.AlbumRoute(album)
                        )
                    }.padding(10.dp)
                    .clip(RoundedCornerShape(25.dp)),
                painter = viewModel.getThumbnails(album.id)?.let {
                    BitmapPainter(it.asImageBitmap())
                } ?: painterResource(R.drawable.music_icon),
                contentDescription = "AlbumArt",
            )
            Text(
                text = album.name,
                modifier = Modifier.basicMarquee()
            )
        }
    }
}
