package com.arshia.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.arshia.musicplayer.data.Preferences
import com.arshia.musicplayer.presentation.permissions.Permissions
import com.arshia.musicplayer.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch { Preferences.getPreferences() }
        setContent {
            AppTheme(theme = Preferences.state.value.appTheme) {
                Permissions(this@MainActivity)
            }
        }
    }

}
