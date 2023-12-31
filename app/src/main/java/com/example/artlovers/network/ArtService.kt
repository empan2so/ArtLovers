package com.example.artlovers.network

import com.example.artlovers.data.model.DetailArtworkResponse
import com.example.artlovers.data.model.ListArtworksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtService {

    /*
     * Get list of artworks
     */
    @GET("/api/v1/artworks?")
    suspend fun listArtworks(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<ListArtworksResponse>

    /*
     * Get list of artworks based on search query
     */
    @GET("/api/v1/artworks/search?")
    suspend fun getArtworksSearchResults(
        @Query("q") query: String,
    ): Response<ListArtworksResponse>

    /*
     * Get detail info about artwork with id
     */
    @GET("/api/v1/artworks/{id}")
    suspend fun getArtworkDetail(
        @Path("id") id: String,
    ): Response<DetailArtworkResponse>

}