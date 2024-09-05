package com.arshia.musicplayer.musicPlayerService

import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.datastore.dataStore
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.arshia.musicplayer.common.arrangeAround
import com.arshia.musicplayer.data.dataSource.AppDataSource
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.repository.serializers.PlayerStateSerializer
import com.arshia.musicplayer.presentation.playerScreen.PlayerState
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
) {

    private val dataStore = context.dataStore
    private val playerState = AppDataSource.playerState
    val mediaController: MediaController?
        get() = if(controllerFuture.isDone) controllerFuture.get()
        else {
            Log.w("controller", "mediaController future called when not ready")
            null
        }
    private val sessionToken = SessionToken(context, ComponentName(context, MusicPlayerService::class.java))
    private var controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
    private var s = true

    init {
        runBlocking {
            playerState.value = dataStore.data.first()
            if(playerState.value.currentTrack == null) {
                playerState.value = PlayerState(
                    currentTrack = AppDataSource.tracksState.value.tracksMap[0],
                    queue = AppDataSource.tracksState.value.tracksMap.values.toList().drop(0)
                )
            }
        }
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
                            currentTrack = AppDataSource.tracksState.value.tracksMap[this.mediaId.toInt()]
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

    fun startMusic(track: TrackItem, playlist: List<TrackItem>) {
        mediaController?.clearMediaItems()
        playerState.value = playerState.value.copy(queue = playlist)
        playlist.arrangeAround(track).forEach {
            mediaController?.addMediaItem(getMediaItem(it))
        }
        mediaController?.prepare()
        mediaController?.play()
    }

    fun togglePauseMusic() {
        if (s) {
            setInitialPlayerState()
            s = false
        }
        if (mediaController?.isPlaying == true) mediaController?.pause()
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

    fun seekTo(i: Long) = mediaController?.seekTo(i)


    private fun setInitialPlayerState() {
        mediaController?.clearMediaItems()
        playerState.value.queue.arrangeAround(playerState.value.currentTrack!!).forEach {
            mediaController?.addMediaItem(getMediaItem(it))
        }
        mediaController?.prepare()
    }

    private fun getMediaItem(track: TrackItem): MediaItem {
        return MediaItem.Builder().setUri(track.uri)
            .setMediaId(track.id.toString())
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtist(track.artist)
                    .setTitle(track.name)
                    .setAlbumTitle(track.album)
                    .build()
            ).build()
    }

    private fun saveState() =  runBlocking { dataStore.updateData { playerState.value.copy(isPlaying = false) } }

}
