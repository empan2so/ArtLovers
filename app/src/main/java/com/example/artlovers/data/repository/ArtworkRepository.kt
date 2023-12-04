package com.example.artlovers.data.repository

import androidx.lifecycle.LiveData
import com.example.artlovers.data.model.Artwork
import kotlinx.coroutines.flow.Flow

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
    suspend fun getArtworkFromRemote(id: String): Flow<Artwork?>
    suspend fun getListArtworkRemote(page: Int, limit: Int): Flow<List<Artwork>>
    suspend fun getSearchResultsRemote(search: String): Flow<List<Artwork>>

}