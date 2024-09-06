package com.arshia.musicplayer.data.model.playlist

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class Converter {

    @TypeConverter
    fun convertListToJson(roomList: RoomList): String {
        return Json.encodeToString(roomList.list)
    }

    @TypeConverter
    fun convertJsonToList(json: String): RoomList {
        return RoomList(list = Json.decodeFromString(json))
    }

}