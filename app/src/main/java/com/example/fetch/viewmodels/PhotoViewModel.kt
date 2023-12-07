package com.example.fetch.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetch.data.Photo
import com.example.fetch.repository.PhotoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PhotoViewModel(
    private val imageRepository: PhotoRepository,
): ViewModel() {

    private val _photos = mutableStateListOf<Photo>()
    var errorMessage: String by mutableStateOf("")
    val photoList: List<Photo>
        get() = _photos

    private val job: Job = viewModelScope.launch {
        imageRepository.fetchPhotos().collect {photos ->
            _photos.clear()
            _photos.addAll(photos)
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    fun getPhoto(): List<Photo> {
        return photoList
    }
}