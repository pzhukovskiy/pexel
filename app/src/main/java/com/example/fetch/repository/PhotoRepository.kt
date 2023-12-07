package com.example.fetch.repository

import com.example.fetch.data.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    suspend fun fetchPhotos(): Flow<List<Photo>>
}