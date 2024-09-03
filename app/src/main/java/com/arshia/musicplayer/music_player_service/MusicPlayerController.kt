package com.arshia.musicplayer.music_player_service

import android.content.ComponentName
import android.content.Context
import android.provider.MediaStore.Audio.Media
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.runtime.mutableStateOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.arshia.musicplayer.common.arrangeAround
import com.arshia.musicplayer.data.data_source.AppDataSource
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.presentation.player_screen.PlayerState
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MusicPlayerController @Inject constructor(
    @ApplicationContext context: Context,
    private val data: AppDataSource
) {

    val playerState = mutableStateOf(PlayerState())

    val mediaController: MediaController?
        get() = if(controllerFuture.isDone) {
            controllerFuture.get()
        } else {
            Log.w("controller", "mediaController future called when not ready")
            null
        }
    private val sessionToken = SessionToken(
        context, ComponentName(context, MusicPlayerService::class.java)
    )
    private val controllerFuture = MediaController.Builder(
        context, sessionToken
    ).buildAsync()

    init {
        listener()
    }

    private fun listener() {
        controllerFuture.addListener({
            mediaController?.addListener(object : Player.Listener {

                @OptIn(UnstableApi::class)
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                    mediaItem?.apply {
                        playerState.value = playerState.value.copy(
                            currentTrack = data.tracksState.value.tracksMap[this.mediaId.toInt()]
                        )
                    }
                    playerState.value = playerState.value.copy(currentPosition = 0)
                }

                override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                    super.onShuffleModeEnabledChanged(shuffleModeEnabled)
                    playerState.value = playerState.value.copy(shuffleMode = shuffleModeEnabled)
                }

                override fun onRepeatModeChanged(repeatMode: Int) {
                    super.onRepeatModeChanged(repeatMode)
                    playerState.value = playerState.value.copy(repeatMode = repeatMode)
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    playerState.value = playerState.value.copy(isPlaying = isPlaying)
                }            })
        }, MoreExecutors.directExecutor())
    }

    fun startMusic(track: TrackItem, playlist: List<TrackItem>) {
        mediaController?.clearMediaItems()
        playlist.arrangeAround(track).forEach {
            mediaController?.addMediaItem(
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
        mediaController?.prepare()
        mediaController?.play()
    }

    fun togglePauseMusic() {
        if(mediaController?.isPlaying == true) mediaController?.pause()
        else mediaController?.play()
    }

    fun nextMusic() = mediaController?.seekToNext()

    fun previousMusic() = mediaController?.seekToPrevious()

    fun toggleRepeatMode() {
        if (mediaController != null) {
            mediaController!!.repeatMode = (mediaController!!.repeatMode + 1) % 3
        }
    }

    fun toggleShuffle() {
        if (mediaController != null) {
            mediaController!!.shuffleModeEnabled = !mediaController!!.shuffleModeEnabled
        }
    }

    fun seekTo(i: Long) {
        mediaController?.seekTo(i)
    }

}