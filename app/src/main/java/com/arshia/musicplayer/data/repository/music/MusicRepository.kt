package com.arshia.musicplayer.data.repository.music

import com.arshia.musicplayer.common.Resource
import kotlinx.coroutines.flow.Flow


interface MusicRepository<T> {

    fun getAll(): Flow<Resource<List<T>>>

}
