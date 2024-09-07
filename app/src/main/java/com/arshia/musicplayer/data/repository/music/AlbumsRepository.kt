@file:Suppress("RemoveExplicitTypeArguments")

package com.arshia.musicplayer.data.repository.music

import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import com.arshia.musicplayer.common.Resource
import com.arshia.musicplayer.data.model.music.AlbumItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AlbumsRepository @Inject constructor(
    @ApplicationContext private val context: Context
): MusicRepository<AlbumItem> {

    override fun getAll(): Flow<Resource<List<AlbumItem>>> = flow {
        try {
            emit(Resource.Loading<List<AlbumItem>>())
            if(Build.VERSION.SDK_INT < 33) {
                if(ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                    emit(Resource.Success<List<AlbumItem>>(emptyList()))
                    return@flow
                }
            }
            val albumsList = mutableListOf<AlbumItem>()
            val queryUri = if(Build.VERSION.SDK_INT > 28) {
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
            } else MediaStore.Audio.Albums.getContentUri("external")

            val projection = arrayOf(
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM
            )

            context.contentResolver.query(
                queryUri, projection, null, null, null
            )?.use { cursor ->
                val id = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID)
                val albumName = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)

                while (cursor.moveToNext()) {
                    val contentUri = ContentUris.withAppendedId(queryUri, cursor.getLong(id))
                    albumsList.add(
                        AlbumItem(
                            uri = contentUri.toString(),
                            id = cursor.getInt(id),
                            name = cursor.getString(albumName),
                        )
                    )
                }
            }
            emit(Resource.Success<List<AlbumItem>>(albumsList.toList()))
        } catch (e: Exception) {
            emit(Resource.Error<List<AlbumItem>>(e.localizedMessage ?: "Error"))
        }
    }

}