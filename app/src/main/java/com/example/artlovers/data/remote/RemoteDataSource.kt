package com.example.artlovers.data.remote

import com.example.artlovers.data.model.Artwork

/*
 * Data source for accessing Art Service api
 */
interface RemoteDataSource {

    suspend fun listArtwork(page: Int, limit: Int): List<Artwork>

    suspend fun listSearchArtwork(query: String): List<Artwork>

    suspend fun getArtworkDetail(id: String): Artwork?

}