package com.example.fetch.repository

import com.example.fetch.data.Photo
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun fetchPhotos(): Flow<List<Photo>>
}