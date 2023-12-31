package com.example.artlovers.data.repository

import androidx.lifecycle.LiveData
import com.example.artlovers.data.model.Artwork
import kotlinx.coroutines.flow.Flow

/*
 * Repository for accessing local and remote Artwork data sources
 */
interface ArtworkRepository {

    val lovedArtwork: LiveData<List<Artwork>?>

    suspend fun updateIsLoved(artwork: Artwork)

    suspend fun refreshArtworkDetail(id: Long, isLoved: Boolean?): Flow<Artwork?>

    suspend fun getHomeArtwork(search: String? = null): Flow<List<Artwork>>

}