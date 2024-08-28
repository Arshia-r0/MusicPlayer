package com.arshia.musicplayer.presentation.settings_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arshia.musicplayer.data.model.settings.Settings
import com.arshia.musicplayer.presentation.main_screen.tabs.components.TopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                title = "Settings",
                navController = navController,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
            )
        }
    ) { ip ->
        SettingsList(settingsViewModel, ip)
    }
}

@Composable
fun SettingsList(settingsViewModel: SettingsViewModel, ip: PaddingValues) {
    val settings by settingsViewModel.settings.collectAsState(initial = Settings())
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(ip)
    ) {
        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { settingsViewModel.changeTheme(settings.appTheme) }
            ) {
                Text("App theme: " + settings.appTheme.name)
            }
        }
    }
}