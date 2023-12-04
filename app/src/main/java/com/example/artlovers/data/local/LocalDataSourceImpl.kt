package com.example.artlovers.data.local

import androidx.lifecycle.LiveData
import com.example.artlovers.data.model.Artwork
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSourceImpl @Inject constructor(
    private val artworkDao : ArtworkDao,
    private val ioDispatcher: CoroutineDispatcher,
): LocalDataSource {
    private val refreshInterval: Long = 5000

    override val lovedArtwork: LiveData<List<Artwork>?> = artworkDao.listArtwork()

    override suspend fun insertListArtwork(artwork: List<Artwork>) = withContext(ioDispatcher){
        return@withContext artwork.forEach { artworkDao.addArtwork(it) }
    }

    override suspend fun listSearchResults(search: String): Flow<List<Artwork>> = flow {
        while (true) {
            emit(artworkDao.listArtworkForSearch(search) ?: emptyList())
            delay(refreshInterval)
        }
    }.flowOn(ioDispatcher)

    override suspend fun getArtwork(id: Long): LiveData<Artwork?> {
        return artworkDao.getArtworkById(id)
    }

    override suspend fun getSubsetArtwork(ids: List<Long>): List<Artwork>? {
        return artworkDao.batchGetArtwork(ids)
    }

    override suspend fun getLovedIds(): List<Long>? {
        return artworkDao.listArtworkIds()
    }

    override suspend fun insertArtwork(artwork: Artwork) = withContext(ioDispatcher){
        return@withContext artworkDao.addArtwork(artwork)
    }

    override suspend fun deleteArtwork(artwork: Artwork) = withContext(ioDispatcher) {
        return@withContext artworkDao.delete(artwork)
    }
}