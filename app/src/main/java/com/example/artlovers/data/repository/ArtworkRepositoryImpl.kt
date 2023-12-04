package com.example.artlovers.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.artlovers.data.local.LocalDataSource
import com.example.artlovers.data.model.Artwork
import com.example.artlovers.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtworkRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : ArtworkRepository {

    override val lovedArtwork: LiveData<List<Artwork>?> =
        localDataSource.lovedArtwork

    override suspend fun getArtworkFromDB(id: Long): LiveData<Artwork?> {
        return localDataSource.getArtwork(id)
    }

    override suspend fun updateIsLoved(artwork: Artwork) {
        localDataSource.insertArtwork(artwork)
        if (artwork.isLoved == false) { localDataSource.deleteArtwork(artwork) }
    }

    override suspend fun getSearchResultsLocal(search: String): LiveData<List<Artwork>?> {
        return localDataSource.listSearchResults(search)
    }

    override suspend fun getArtworkFromRemote(id: String): Flow<Artwork?> {
        return remoteDataSource.getArtworkDetail(id)
    }

    override suspend fun getListArtworkRemote(page: Int, limit: Int): Flow<List<Artwork>> {
        Log.i("ArtworkRepositoryImpl", "getting home list")
        val loved = localDataSource.getLovedIds()
        return remoteDataSource.listArtwork(page, limit).map { list ->
            list.map { artwork ->
                if (loved?.contains(artwork.id) == true) {
                    artwork.isLoved = true
                }
                artwork
            }
        }
    }

    override suspend fun getSearchResultsRemote(search: String): Flow<List<Artwork>> {
        Log.i("ArtworkRepositoryImpl", "getting search list")
        val loved = localDataSource.getLovedIds()
        return remoteDataSource.listSearchArtwork(search).map { list ->
            list.map { artwork ->
                if (loved?.contains(artwork.id) == true) {
                    artwork.isLoved = true
                }
                artwork
            }
        }
    }

}