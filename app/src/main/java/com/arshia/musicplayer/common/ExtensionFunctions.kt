package com.arshia.musicplayer.common

import com.arshia.musicplayer.data.model.music.TrackItem


fun List<TrackItem>.arrangeAround(track: TrackItem): List<TrackItem> {
    val tmp = this.toMutableList()
    for(t in this) {
        if(t == track) break
        tmp.remove(t)
        tmp.add(t)
    }
    return tmp
}

inline fun <reified T: Enum<T>> T.next(): T {
    val values = enumValues<T>()
    val nextOrdinal = (ordinal + 1) % values.size
    return values[nextOrdinal]
}

fun Long.convertToTime(): String {
    val sec = this / 1000
    val minutes = sec / 60
    val seconds = sec % 60

    val minutesString = if (minutes < 10) {
        "0$minutes"
    } else {
        minutes.toString()
    }
    val secondsString = if (seconds < 10) {
        "0$seconds"
    } else {
        seconds.toString()
    }
    return "$minutesString:$secondsString"
}
