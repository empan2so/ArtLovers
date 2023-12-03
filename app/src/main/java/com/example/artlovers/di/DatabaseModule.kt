package com.example.artlovers.di

import android.content.Context
import com.example.artlovers.data.local.ArtDatabase
import com.example.artlovers.data.local.ArtworkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ArtDatabase =
        ArtDatabase.create(context)

    @Provides
    @Singleton
    fun provideDao(database: ArtDatabase): ArtworkDao =
        database.artworkDao()
}