package com.arshia.musicplayer.di

import android.app.Application
import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.arshia.musicplayer.data.data_source.AppDataSource
import com.arshia.musicplayer.music_player_service.MusicPlayerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(context: Application): Context = context

    @Provides
    @Singleton
    fun provideDataSource(): AppDataSource = AppDataSource()

    @Provides
    @Singleton
    fun provideMusicPLayerService(): MusicPlayerService = MusicPlayerService()

    @Provides
    @Singleton
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }

}
