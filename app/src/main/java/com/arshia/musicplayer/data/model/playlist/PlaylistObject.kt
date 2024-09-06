package com.arshia.musicplayer.data.model.playlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Entity(tableName = "playlist_table")
data class PlaylistObject(
    val name: String,
    val tracks: RoomList,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)

@Serializable
data class RoomList(
    val list: List<String> = emptyList()
)