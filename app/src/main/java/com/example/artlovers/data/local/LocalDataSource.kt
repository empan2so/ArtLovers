package com.example.artlovers.data.local

import androidx.lifecycle.LiveData
import com.example.artlovers.data.model.Artwork
import kotlinx.coroutines.flow.Flow

/*
 * Data source for accessing local db
 */
interface LocalDataSource {

    val lovedArtwork: LiveData<List<Artwork>?>

    suspend fun listSearchResults(search: String): Flow<List<Artwork>>

    suspend fun getArtwork(id: Long): LiveData<Artwork?>
    suspend fun getSubsetArtwork(ids: List<Long>): List<Artwork>?
    suspend fun getLovedIds(): List<Long>?

    suspend fun insertListArtwork(artwork: List<Artwork>)
    suspend fun insertArtwork(artwork: Artwork)
    suspend fun deleteArtwork(artwork: Artwork)

}