package com.example.artlovers.data.repository

import androidx.lifecycle.LiveData
import com.example.artlovers.data.model.Artwork

/*
 * Repository for accessing local and remote Artwork data sources
 */
interface ArtworkRepository {

    // Local
    val lovedArtwork: LiveData<List<Artwork>?>
    suspend fun getArtworkFromDB(id: Long): LiveData<Artwork?>
    suspend fun updateIsLoved(artwork: Artwork)
    suspend fun getSearchResultsLocal(search: String): LiveData<List<Artwork>>

    // Remote
    suspend fun getArtworkFromRemote(id: String): Artwork?
    suspend fun getListArtworkRemote(page: Int, limit: Int): List<Artwork>
    suspend fun getSearchResultsRemote(search: String): List<Artwork>

}