package com.arshia.musicplayer.presentation.navigation


sealed class Routes(val route: String) {

    data object MainRoute: Routes("main_route")

    data object PlayerRoute: Routes("player_route")

    data object ListRoute: Routes("list_route")

    data object SettingRoute: Routes("setting_route")

}