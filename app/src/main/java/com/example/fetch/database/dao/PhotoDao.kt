package com.example.fetch.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.fetch.data.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {
    @Upsert
    suspend fun upsertPhoto(photo: Photo)

    @Delete
    suspend fun deleteItem(photo: Photo)

    @Query("SELECT * FROM photo ORDER BY url ASC")
    fun getPhotosByURL(): Flow<List<Photo>>
}