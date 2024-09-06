package com.arshia.musicplayer.data.repository.playlists

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arshia.musicplayer.data.model.playlist.Converter
import com.arshia.musicplayer.data.model.playlist.PlaylistObject


@TypeConverters(value = [Converter::class])
@Database(
    entities = [PlaylistObject::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        const val NAME = "DB"
    }

    abstract fun getPlaylistDao(): PlaylistDao

}
