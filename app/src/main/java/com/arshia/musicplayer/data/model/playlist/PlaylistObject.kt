package com.arshia.musicplayer.data.model.playlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arshia.musicplayer.data.model.music.TrackItem
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "playlist_table")
data class PlaylistObject(
    val name: String,
    val list: Set<TrackItem>,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
