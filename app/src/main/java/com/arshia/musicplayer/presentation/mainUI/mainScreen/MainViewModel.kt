package com.arshia.musicplayer.presentation.mainUI.mainScreen

import androidx.lifecycle.ViewModel
import com.arshia.musicplayer.presentation.mainUI.appData.AppdataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    val data: AppdataSource,
): ViewModel() {

    val tab = data.tab

}
