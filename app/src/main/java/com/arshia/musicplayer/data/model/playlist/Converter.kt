package com.arshia.musicplayer.data.model.playlist

import androidx.room.TypeConverter
import com.arshia.musicplayer.data.model.music.TrackItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class Converter {

    @TypeConverter
    fun convertListToJson(trackItem: TrackItem): String {
        return Json.encodeToString(trackItem)
    }

    @TypeConverter
    fun convertJsonToList(json: String): TrackItem {
        return Json.decodeFromString(json)
    }

}