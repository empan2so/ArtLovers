package com.example.artlovers.data.local

import androidx.lifecycle.LiveData
import com.example.artlovers.data.model.Artwork
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSourceImpl @Inject constructor(
    private val artworkDao : ArtworkDao,
    private val ioDispatcher: CoroutineDispatcher,
): LocalDataSource {

    override val lovedArtwork: LiveData<List<Artwork>?> = artworkDao.listArtwork()

    override suspend fun listSearchResults(search: String): LiveData<List<Artwork>?> {
        return artworkDao.listArtworkForSearch(search)
    }

    override suspend fun getArtwork(id: Long): LiveData<Artwork?> {
        return artworkDao.getArtworkById(id)
    }

    override suspend fun getLovedIds(): List<Long>? {
        return artworkDao.listArtworkIds()
    }

    override suspend fun insertArtwork(artwork: Artwork) = withContext(ioDispatcher){
        return@withContext artworkDao.addArtwork(artwork)
    }

    override suspend fun insertListArtwork(artwork: List<Artwork>) = withContext(ioDispatcher){
        return@withContext artwork.forEach { artworkDao.addArtwork(it) }
    }

    override suspend fun deleteArtwork(artwork: Artwork) = withContext(ioDispatcher) {
        return@withContext artworkDao.delete(artwork)
    }
}