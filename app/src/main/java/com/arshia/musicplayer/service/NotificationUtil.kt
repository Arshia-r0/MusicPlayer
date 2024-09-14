package com.arshia.musicplayer.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build


@Suppress("ConstPropertyName")
object NotificationUtil {

    private const val channelID = "player_notification"
    private const val channelName = "Music Player"

    fun createChannel(context: Context) {
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            channelID, channelName, NotificationManager.IMPORTANCE_MIN
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            channel.setAllowBubbles(false)
        }
        channel.setBypassDnd(true)
        notificationManager.createNotificationChannel(channel)
    }

}
