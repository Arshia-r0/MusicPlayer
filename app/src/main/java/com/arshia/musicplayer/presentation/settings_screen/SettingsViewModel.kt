package com.arshia.musicplayer.presentation.settings_screen

import android.content.Context
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.musicplayer.common.next
import com.arshia.musicplayer.data.model.settings.Themes
import com.arshia.musicplayer.data.repository.settings.SettingsSerializer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


private val Context.dataStore by dataStore(
    "app-Settings.json",
    SettingsSerializer
)


@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext context: Context
): ViewModel() {

    private val dataStore = context.dataStore
    val settings = dataStore.data

    fun changeTheme(currentTheme: Themes) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.updateData {
                it.copy(appTheme = currentTheme.next<Themes>())
            }
        }
    }

}