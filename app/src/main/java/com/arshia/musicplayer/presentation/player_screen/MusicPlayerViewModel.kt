package com.arshia.musicplayer.presentation.player_screen

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import com.arshia.musicplayer.common.arrangeAround
import com.arshia.musicplayer.data.data_source.AppDataSource
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.music_player_service.MusicPlayerService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    val data: AppDataSource,
    musicPlayerService: MusicPlayerService
): ViewModel() {

    private val player = musicPlayerService.player

    val currentTrack = mutableStateOf<TrackItem?>(null)
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
                    mediaItem?.let {
                        currentTrack.value = data.tracksState.value.tracksMap[mediaItem.mediaId.toInt()]
                    }
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

    fun startMusic(track: TrackItem, playlist: List<TrackItem>) {
        player.clearMediaItems()
        playlist.arrangeAround(track).forEach {
            player.addMediaItem(
                MediaItem.Builder().setUri(it.uri)
                    .setMediaId(it.id.toString())
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setTitle(it.name)
                            .setArtist(it.artist)
                            .setAlbumTitle(it.album)
                            .build()
                    ).build()
            )
        }
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
