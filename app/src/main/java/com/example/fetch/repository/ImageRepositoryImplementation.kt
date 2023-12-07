package com.example.fetch.repository

import com.example.fetch.api.FetchService
import com.example.fetch.data.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImageRepositoryImplementation: ImageRepository {
    override suspend fun fetchPhotos(): Flow<List<Photo>> {
        return flow {
            val photos = FetchService.create().getPhotos().photos
            emit(photos)
        }
    }
}