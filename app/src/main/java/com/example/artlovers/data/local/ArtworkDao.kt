package com.example.artlovers.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.artlovers.data.model.Artwork

@Dao
interface ArtworkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArtwork(vararg artwork: Artwork)

    @Query("SELECT * FROM artwork ORDER BY title DESC")
    fun listArtwork(): LiveData<List<Artwork>?>

//    @Query("SELECT * FROM artwork WHERE isLoved ORDER BY title DESC")
//    fun listLovedArtwork(): LiveData<List<Artwork>?>

    @Query("SELECT * FROM artwork WHERE id IN (:ids) ")
    fun batchGetArtwork(ids: List<Long>): List<Artwork>?

    @Query("SELECT id FROM artwork")
    fun listArtworkIds(): List<Long>?

    @Query("SELECT * FROM artwork WHERE title LIKE :search")
    fun listArtworkForSearch(search: String): List<Artwork>?

    @Query("SELECT * FROM artwork WHERE id=:id LIMIT 1")
    fun getArtworkById(id: Long): LiveData<Artwork?>

    @Delete
    fun delete(artwork: Artwork)
}