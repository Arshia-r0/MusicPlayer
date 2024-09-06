package com.arshia.musicplayer.data.repository.thumbnail

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ThumbnailsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getThumbnail(uri: String): Bitmap? {
       return retrieveThumbnail(Uri.parse(uri))
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getThumbnail(uri: Uri): Bitmap? {
        return retrieveThumbnail(uri)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun retrieveThumbnail(uri: Uri): Bitmap? {
        return try {
            if(Build.VERSION.SDK_INT < 33) {
                if(ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED) {
                    throw Exception()
                }
            }
            context.contentResolver.loadThumbnail(
                uri, Size(500, 500), null
            )
        } catch (e: Exception) { null }
    }
}