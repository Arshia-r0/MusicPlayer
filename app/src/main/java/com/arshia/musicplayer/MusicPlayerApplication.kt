package com.arshia.musicplayer

import android.app.Application
import androidx.room.Room
import com.arshia.musicplayer.data.repository.playlists.AppDatabase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MusicPlayerApplication: Application() {

    companion object {

        lateinit var context: MusicPlayerApplication
            private set

        lateinit var database: AppDatabase

    }

    override fun onCreate() {
        super.onCreate()
        context = this
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.NAME
        ).build()
    }

}
