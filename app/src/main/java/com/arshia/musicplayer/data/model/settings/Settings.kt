package com.arshia.musicplayer.data.model.settings

import kotlinx.serialization.Serializable


@Serializable
data class Settings(
    val appTheme: Themes = Themes.System
)
