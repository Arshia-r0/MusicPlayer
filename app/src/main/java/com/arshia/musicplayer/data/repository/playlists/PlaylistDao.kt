package com.arshia.musicplayer.data.repository.playlists

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import kotlinx.coroutines.flow.Flow


@Dao
interface PlaylistDao {

    @Query("SELECT * FROM playlist_table")
    fun getAll(): Flow<List<PlaylistObject>>

    @Insert
    fun create(item: PlaylistObject)

    @Query("DELETE FROM playlist_table WHERE id = :id")
    fun deletePlaylist(id: Int)

    @Update
    fun update(item: PlaylistObject)

}