package com.arshia.musicplayer.service

import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.dataStore
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.arshia.musicplayer.common.arrangeAround
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.repository.serializers.PlayerStateSerializer
import com.arshia.musicplayer.presentation.mainUI.mainData.MainData
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore by dataStore(
    "player-state.json",
    PlayerStateSerializer
)

@Singleton
class MusicPlayerController @Inject constructor(
    @ApplicationContext private val context: Context,
    private val data: MainData
) {

    val playerState = mutableStateOf(PlayerState())

    private val dataStore = context.dataStore
    val mediaController: MediaController?
        get() = if(controllerFuture.isDone) controllerFuture.get()
        else {
            Log.w("controller", "mediaController future called when not ready")
            null
        }
    private val sessionToken = SessionToken(context, ComponentName(context, MusicPlayerService::class.java))
    private var controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()


    init {
        runBlocking {
            try { playerState.value = dataStore.data.first() }
            catch (_: NoSuchElementException) {}
            if(playerState.value.currentTrack == null) {
                playerState.value = PlayerState(
                    currentTrack = data.tracksState.value.tracksMap.values.first(),
                    queue = data.tracksState.value.tracksMap.values.toList()
                )
            }
        }
        addCallback()
        listener()
    }

    private fun saveState() {
        runBlocking {
            dataStore.updateData { playerState.value.copy(isPlaying = false) }
        }
    }

    private fun addCallback() {
        Futures.addCallback(
            controllerFuture,
            object : FutureCallback<MediaController> {

                override fun onSuccess(result: MediaController?) = setInitialPlayerState()

                override fun onFailure(t: Throwable) {}

            },
            context.mainExecutor
        )
    }

    private fun listener() {
        controllerFuture.addListener({
            mediaController?.addListener(object : Player.Listener {

                @OptIn(UnstableApi::class)
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                    mediaItem?.apply {
                        playerState.value = playerState.value.copy(
                            currentTrack = data.tracksState.value.tracksMap[this.mediaId.toInt()],
                            currentPosition = 0
                        )
                    }
                    playerState.value = playerState.value.copy(currentPosition = 0)
                    saveState()
                }

                override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                    super.onShuffleModeEnabledChanged(shuffleModeEnabled)
                    playerState.value = playerState.value.copy(shuffleMode = shuffleModeEnabled)
                    saveState()
                }

                override fun onRepeatModeChanged(repeatMode: Int) {
                    super.onRepeatModeChanged(repeatMode)
                    playerState.value = playerState.value.copy(repeatMode = repeatMode)
                    saveState()
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    playerState.value = playerState.value.copy(isPlaying = isPlaying)
                    saveState()
                }
            })
        }, MoreExecutors.directExecutor())
    }

    private fun setInitialPlayerState() {
        mediaController?.clearMediaItems()
        playerState.value.queue.arrangeAround(playerState.value.currentTrack!!).forEach {
            mediaController?.addMediaItem(getMediaItem(it))
        }
        mediaController?.prepare()
    }

    private fun getMediaItem(track: TrackItem): MediaItem =
        MediaItem.Builder().setUri(track.uri)
            .setMediaId(track.id.toString())
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtist(track.artist)
                    .setTitle(track.name)
                    .setAlbumTitle(track.album)
                    .build()
            ).build()

    inner class Commands {

        fun togglePauseMusic() {
            if (mediaController?.isPlaying == true) mediaController?.pause()
            else mediaController?.play()
        }

        fun toggleShuffle() {
            if (mediaController != null) {
                mediaController!!.shuffleModeEnabled = !mediaController!!.shuffleModeEnabled
            }
        }

        fun toggleRepeatMode() {
            if (mediaController != null) {
                mediaController!!.repeatMode = (mediaController!!.repeatMode + 1) % 3
            }
        }

        fun nextMusic() = mediaController?.seekToNext()

        fun previousMusic() = mediaController?.seekToPrevious()

        fun startMusic(track: TrackItem, playlist: List<TrackItem>) {
            mediaController?.clearMediaItems()
            playerState.value = playerState.value.copy(queue = playlist)
            playlist.arrangeAround(track).forEach {
                mediaController?.addMediaItem(getMediaItem(it))
            }
            mediaController?.prepare()
            mediaController?.play()
        }

        fun seekTo(i: Long) = mediaController?.seekTo(i)

    }

}
