package com.arshia.musicplayer.presentation.mainUI.mainScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.arshia.musicplayer.presentation.mainUI.mainData.MainData
import com.arshia.musicplayer.presentation.mainUI.mainScreen.components.MainTabs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    val data: MainData,
): ViewModel() {

    val selectedTab = mutableStateOf(MainTabs.Playlists)

}
