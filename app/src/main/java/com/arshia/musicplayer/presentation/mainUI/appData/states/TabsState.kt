package com.arshia.musicplayer.presentation.mainUI.appData.states


enum class TabsState(val title: String) {

    Albums("Albums"),
    Playlists("Playlists"),
    Tracks("Tracks");

    var selectionMode = false

    fun changeViewMode(): Boolean {
        selectionMode = !selectionMode
        return selectionMode
    }

}
