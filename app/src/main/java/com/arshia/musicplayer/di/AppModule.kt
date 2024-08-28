package com.arshia.musicplayer.di

import android.app.Application
import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(context: Application): Context = context

    @Provides
    @Singleton
    fun providePlayer(context: Application): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }

}
