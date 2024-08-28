package com.arshia.musicplayer.presentation.player_screen

import androidx.annotation.OptIn
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.arshia.musicplayer.common.arrangeAround
import com.arshia.musicplayer.data.model.TrackItem
import com.arshia.musicplayer.music_player_service.MusicPlayerService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    musicPlayerService: MusicPlayerService
): ViewModel() {

    private val player = musicPlayerService.player

    val currentTrack = mutableStateOf(MediaItem.Builder().build())
    val shuffleMode = mutableStateOf(false)
    val musicRepeatMode = mutableIntStateOf(0)
    val musicIsPlaying = mutableStateOf(false)

    init {
        listen()
    }

    private fun listen() {
        player.addListener(
            object : Player.Listener {

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                    mediaItem?.let { currentTrack.value = it }
                }

                override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                    super.onShuffleModeEnabledChanged(shuffleModeEnabled)
                    shuffleMode.value = shuffleModeEnabled
                }

                override fun onRepeatModeChanged(repeatMode: Int) {
                    super.onRepeatModeChanged(repeatMode)
                    musicRepeatMode.intValue = repeatMode
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    musicIsPlaying.value = isPlaying
                }

            }
        )
    }

    @OptIn(UnstableApi::class)
    fun startMusic(track: TrackItem, playlist: List<TrackItem>) {
//        val notification = MediaNotification(
//            1,
//            Notification.Builder(playerService, "MusicPlayer").build()
//        )
//        playerService.startForeground(1, notification.notification)
        player.clearMediaItems()
        playlist.arrangeAround(track).forEach { player.addMediaItem(it.mediaItem) }
        player.prepare()
        player.play()
    }

    fun togglePauseMusic() {
        if(player.isPlaying) player.pause()
        else player.play()
    }

    fun nextMusic() {
        player.seekToNext()
    }

    fun previousMusic() {
        player.seekToPrevious()
    }

    fun toggleRepeatMode() {
        player.repeatMode = (player.repeatMode + 1) % 3
    }

    fun toggleShuffle() {
        player.shuffleModeEnabled = !player.shuffleModeEnabled
    }

}


