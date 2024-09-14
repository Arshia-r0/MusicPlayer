package com.arshia.musicplayer.data

import android.content.Context
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.arshia.musicplayer.MusicPlayerApplication
import com.arshia.musicplayer.common.next
import com.arshia.musicplayer.data.model.settings.Settings
import com.arshia.musicplayer.data.model.settings.Themes
import com.arshia.musicplayer.data.repository.serializers.SettingsSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


private val Context.dataSore by dataStore(
    "settings.json",
    SettingsSerializer
)

object Preferences {

    val state = mutableStateOf(Settings())

    private val dataStore: DataStore<Settings> = MusicPlayerApplication.context.dataSore
    private val scope = CoroutineScope(Dispatchers.IO)

    fun changeTheme(currentTheme: Themes) {
        scope.launch {
            val newTheme = currentTheme.next()
            if(Build.VERSION.SDK_INT < 31 && newTheme == Themes.DynamicColor)
                newTheme.next()
            dataStore.updateData {
                it.copy(appTheme = newTheme)
            }
        }
    }

    fun getPreferences() {
        dataStore.data.onEach {
            state.value = state.value.copy(appTheme = it.appTheme)
        }.launchIn(scope)
    }

}
