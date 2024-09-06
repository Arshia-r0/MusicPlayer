package com.arshia.musicplayer.presentation.mainScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arshia.musicplayer.presentation.mainScreen.tabs.albums.AlbumsTab
import com.arshia.musicplayer.presentation.mainScreen.tabs.components.TopBar
import com.arshia.musicplayer.presentation.mainScreen.tabs.playlists.PlayListsTab
import com.arshia.musicplayer.presentation.mainScreen.tabs.tracks.TracksTab
import com.arshia.musicplayer.presentation.navigation.Routes
import com.arshia.musicplayer.presentation.mainScreen.playerScreen.BottomBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel,
    ) {
    val tabs = listOf("Albums", "Playlists", "Tracks")
    val pagerState = rememberPagerState { tabs.size }
    val selectedTabIndex by viewModel.selectedTabIndex

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController, viewModel) },
        topBar = { TopBar(
            title = "Rumbar",
            actions = {
                IconButton(onClick = { navController.navigate(Routes.SettingRoute.route) }) {
                    Icon(imageVector = Icons.Filled.Settings, contentDescription = "settings")
                }
            }
        ) }
    ) { ip ->
        LaunchedEffect(selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
        LaunchedEffect(selectedTabIndex, pagerState.isScrollInProgress) {
            if(!pagerState.isScrollInProgress) {
                viewModel.changeTab(pagerState.currentPage)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = true,
                        onClick = { viewModel.changeTab(index)},
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        text = { Text(
                            text = tab,
                            fontSize = 15.sp,
                        ) }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            ) { i ->
                when (i) {
                    0 -> AlbumsTab(viewModel, navController)
                    1 -> PlayListsTab(viewModel, navController)
                    2 -> TracksTab(viewModel)
                }
            }
        }
    }
}
