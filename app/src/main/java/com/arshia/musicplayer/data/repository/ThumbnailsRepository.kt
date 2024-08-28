package com.arshia.musicplayer.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Size
import androidx.core.content.ContextCompat
import javax.inject.Inject
import kotlin.Exception


class ThumbnailsRepository @Inject constructor(
    private val appContext: Context
) {

    @SuppressLint("NewApi")
    fun getThumbnail(uri: Uri): Bitmap? {
        return try {
            if(Build.VERSION.SDK_INT < 33) {
                if(ContextCompat.checkSelfPermission(
                        appContext,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED) {
                    throw Exception()
                }
            }
            appContext.contentResolver.loadThumbnail(
                uri, Size(500, 500), null
            )
        } catch (e: Exception) { null }
    }
}