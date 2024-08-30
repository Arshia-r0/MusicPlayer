package com.arshia.musicplayer.music_player_service

import android.app.Notification
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.annotation.OptIn
import androidx.core.app.ServiceCompat
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import javax.inject.Inject


class MusicPlayerService @Inject constructor(
    val player: ExoPlayer
): MediaSessionService() {

    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSession.Builder(this, player).build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        if (!player.playWhenReady
            || player.mediaItemCount == 0
            || player.playbackState == Player.STATE_ENDED) {
            stopSelf()
        }
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession?.release()
        }
        super.onDestroy()
    }

    @OptIn(UnstableApi::class)
    fun start() {
        try {
            val notification = Notification
                .Builder(this, "Music Player")
                .setStyle(Notification.MediaStyle()
                    .setMediaSession(mediaSession?.platformToken))
                .build()
            ServiceCompat.startForeground(
                this,
                1,
                notification,
                if (Build.VERSION.SDK_INT > 28) {
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
                } else 0
            )
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }

}
