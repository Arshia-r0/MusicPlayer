package com.arshia.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.arshia.musicplayer.data.model.settings.Settings
import com.arshia.musicplayer.presentation.permissions.Permissions
import com.arshia.musicplayer.presentation.settings_screen.SettingsViewModel
import com.arshia.musicplayer.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val theme by settingsViewModel.settings.collectAsState(initial = Settings())
            AppTheme(theme = theme.appTheme) {
                Permissions(this)
            }
        }
    }

}
