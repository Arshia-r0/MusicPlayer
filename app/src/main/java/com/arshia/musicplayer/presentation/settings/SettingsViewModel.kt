package com.arshia.musicplayer.presentation.settings

import android.content.Context
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.musicplayer.common.next
import com.arshia.musicplayer.data.model.settings.Settings
import com.arshia.musicplayer.data.model.settings.Themes
import com.arshia.musicplayer.data.repository.serializers.SettingsSerializer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


private val Context.dataStore by dataStore(
    "settings.json",
    SettingsSerializer
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext context: Context,
): ViewModel() {

    private val dataStore = context.dataStore
    val settings = mutableStateOf(Settings())

    init {
        getPreferences()
    }

    fun changeTheme(currentTheme: Themes) {
        viewModelScope.launch(Dispatchers.IO) {
            val newTheme = currentTheme.next()
            if(Build.VERSION.SDK_INT < 31 && newTheme == Themes.DynamicColor)
                newTheme.next()
            dataStore.updateData {
                it.copy(appTheme = newTheme)
            }
        }
    }

    private fun getPreferences() {
        dataStore.data.onEach { result ->
            settings.value = settings.value.copy(appTheme = result.appTheme)
        }.launchIn(viewModelScope)
    }

}
