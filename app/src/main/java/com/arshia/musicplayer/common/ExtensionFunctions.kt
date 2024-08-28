package com.arshia.musicplayer.common

import com.arshia.musicplayer.data.model.TrackItem


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
