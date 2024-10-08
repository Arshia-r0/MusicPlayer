package com.arshia.musicplayer.presentation.settings

import androidx.lifecycle.ViewModel
import com.arshia.musicplayer.data.Preferences
import com.arshia.musicplayer.data.model.settings.Themes


class SettingsViewModel: ViewModel() {

    val settings = Preferences.state

    fun changeTheme(theme: Themes) = Preferences.changeTheme(theme)

}
