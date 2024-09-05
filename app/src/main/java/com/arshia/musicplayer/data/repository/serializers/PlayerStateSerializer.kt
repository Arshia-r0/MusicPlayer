package com.arshia.musicplayer.data.repository.serializers

import androidx.datastore.core.Serializer
import com.arshia.musicplayer.presentation.playerScreen.PlayerState
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


object PlayerStateSerializer: Serializer<PlayerState> {

    override val defaultValue: PlayerState
        get() = PlayerState()

    override suspend fun readFrom(input: InputStream): PlayerState {
        return try {
            Json.decodeFromString(
                deserializer = PlayerState.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: PlayerState, output: OutputStream) {
        @Suppress("BlockingMethodInNonBlockingContext")
        output.write(
            Json.encodeToString(
                serializer = PlayerState.serializer(),
                value = t,
            ).encodeToByteArray()
        )
    }

}