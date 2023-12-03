package com.example.artlovers.di

import com.example.artlovers.data.local.LocalDataSource
import com.example.artlovers.data.local.LocalDataSourceImpl
import com.example.artlovers.data.remote.RemoteDataSource
import com.example.artlovers.data.remote.RemoteDataSourceImpl
import com.example.artlovers.data.repository.ArtworkRepository
import com.example.artlovers.data.repository.ArtworkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    abstract fun bindArtworkRepository(
        artworkRepositoryImpl: ArtworkRepositoryImpl
    ): ArtworkRepository

}