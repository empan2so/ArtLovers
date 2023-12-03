package com.example.artlovers.data.remote

import android.util.Log
import com.example.artlovers.data.model.Artwork
import com.example.artlovers.data.model.toArtworkModel
import com.example.artlovers.network.ArtService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val artService: ArtService,
) : RemoteDataSource {

    override suspend fun listArtwork(page: Int, limit: Int): List<Artwork> {
        var artwork = emptyList<Artwork>()
        try {
            artService.listArtworks(page, limit)
                .body()?.artworkList?.map { it.toArtworkModel() }?.let {
                    artwork = it
                }
        } catch (exception: Exception) {
            // TODO handle exceptions
            Log.w("RemoteDataSourceImpl", "Exception at fetchArtwork(): ${exception.message}")
        }
        return artwork
    }

    override suspend fun listSearchArtwork(query: String): List<Artwork> {
        var artwork = emptyList<Artwork>()
        try {
            artService.getArtworksSearchResults(query)
                .body()?.artworkList?.map { it.toArtworkModel() }?.let {
                    artwork = it
                }
        } catch (exception: Exception) {
            // TODO handle exceptions
            Log.w("RemoteDataSourceImpl", "Exception at searchArtwork(${query}): ${exception.message}")
        }
        return artwork
    }

    override suspend fun getArtworkDetail(id: String): Artwork? {
        return try {
            artService.getArtworkDetail(id).body()?.data?.toArtworkModel()
        } catch (exception: Exception) {
            // TODO handle exceptions
            Log.w("RemoteDataSourceImpl", "Exception at getArtworkDetail(${id}): ${exception.message}")
            null
        }
    }
}