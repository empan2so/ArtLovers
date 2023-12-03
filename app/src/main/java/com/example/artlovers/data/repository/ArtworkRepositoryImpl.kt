package com.example.artlovers.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.artlovers.data.local.LocalDataSource
import com.example.artlovers.data.model.Artwork
import com.example.artlovers.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtworkRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
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

    override suspend fun getSearchResultsLocal(search: String): LiveData<List<Artwork>> {
        return localDataSource.listSearchResults(search).asLiveData(ioDispatcher)
    }

    override suspend fun getArtworkFromRemote(id: String): Artwork? {
        return remoteDataSource.getArtworkDetail(id)
    }

    override suspend fun getListArtworkRemote(page: Int, limit: Int): List<Artwork> {
        return mergeFetchedListWithDB(remoteDataSource.listArtwork(page, limit))
    }

    override suspend fun getSearchResultsRemote(search: String): List<Artwork> {
        return mergeFetchedListWithDB(remoteDataSource.listSearchArtwork(search))
    }

    // Merges remotely fetched list with isLoved data from DB
    private suspend fun mergeFetchedListWithDB(list: List<Artwork>) : List<Artwork> {
        val fetchedIds = list.map { it.id }
        val lovedArtworksIds = localDataSource.getSubsetArtwork(fetchedIds)?.map { it.id }
        for (artwork in list) {
            if (lovedArtworksIds?.contains(artwork.id) == true) {
                artwork.isLoved = true
            }
        }
        return list
    }
}