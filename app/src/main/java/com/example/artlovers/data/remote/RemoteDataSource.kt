package com.example.artlovers.data.remote

import com.example.artlovers.data.model.Artwork
import kotlinx.coroutines.flow.Flow

/*
 * Data source for accessing Art Service api
 */
interface RemoteDataSource {

    suspend fun listArtwork(page: Int, limit: Int): Flow<List<Artwork>>

    suspend fun listSearchArtwork(query: String): Flow<List<Artwork>>

    suspend fun getArtworkDetail(id: String): Flow<Artwork?>

}