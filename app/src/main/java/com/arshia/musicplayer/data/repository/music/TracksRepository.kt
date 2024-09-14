@file:Suppress("RemoveExplicitTypeArguments")

package com.arshia.musicplayer.data.repository.music

import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import com.arshia.musicplayer.common.Resource
import com.arshia.musicplayer.data.model.music.TrackItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class TracksRepository @Inject constructor(
    @ApplicationContext private val context: Context
): MusicRepository<TrackItem> {

    override fun getAll(): Flow<Resource<List<TrackItem>>> = flow {
        try {
            emit(Resource.Loading<List<TrackItem>>())
            if(Build.VERSION.SDK_INT < 33) {
                if(ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED) {
                    emit(Resource.Success<List<TrackItem>>(emptyList()))
                    return@flow
                }
            }
            val tracksList = mutableListOf<TrackItem>()
            val queryUri = if(Build.VERSION.SDK_INT > 28) {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            } else MediaStore.Audio.Media.getContentUri("external")

            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID
            )

            val sortOrder = "_DISPLAY_NAME ASC"

            context.contentResolver.query(
                queryUri, projection, null, null, sortOrder
            )?.use { cursor ->
                val id = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val name = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                val size = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
                val artist = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val duration = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                val album = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
                val albumId = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

                while(cursor.moveToNext()) {
                    val contentUri = ContentUris.withAppendedId(queryUri, cursor.getLong(id))
                    tracksList.add(
                        TrackItem(
                            uri = contentUri.toString(),
                            id = cursor.getInt(id),
                            name = cursor.getString(name),
                            size = cursor.getInt(size),
                            artist = cursor.getString(artist),
                            duration = cursor.getLong(duration),
                            album = cursor.getString(album),
                            albumId = cursor.getInt(albumId),
                        )
                    )
                }
            }
            emit(Resource.Success<List<TrackItem>>(tracksList.toList()))
        } catch (e: Exception) {
            emit(Resource.Error<List<TrackItem>>(e.localizedMessage ?: "Error"))
        }
    }

}