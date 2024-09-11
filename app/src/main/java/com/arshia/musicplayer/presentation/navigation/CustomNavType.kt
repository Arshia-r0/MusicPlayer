package com.arshia.musicplayer.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.arshia.musicplayer.data.model.music.AlbumItem
import com.arshia.musicplayer.data.model.music.TrackItem
import com.arshia.musicplayer.data.model.playlist.PlaylistObject
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


object CustomNavType {

    val AlbumItemType = object : NavType<AlbumItem>(
        isNullableAllowed = false
    ) {

        override fun get(bundle: Bundle, key: String): AlbumItem? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): AlbumItem {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: AlbumItem) {
            bundle.putString(key, Json.encodeToString(value))

        }

        override fun serializeAsValue(value: AlbumItem): String {
            return Uri.encode(Json.encodeToString(value))
        }

    }

    val PlaylistObjectType = object : NavType<PlaylistObject>(
        isNullableAllowed = false
    ) {

        override fun get(bundle: Bundle, key: String): PlaylistObject? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): PlaylistObject {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: PlaylistObject) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: PlaylistObject): String {
            return Uri.encode(Json.encodeToString(value))
        }

    }

    val TrackItemType = object : NavType<Set<TrackItem>>(
        isNullableAllowed = false
    ) {

        override fun get(bundle: Bundle, key: String): Set<TrackItem>? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Set<TrackItem >{
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: Set<TrackItem>) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: Set<TrackItem>): String {
            return Uri.encode(Json.encodeToString(value))
        }

    }

}