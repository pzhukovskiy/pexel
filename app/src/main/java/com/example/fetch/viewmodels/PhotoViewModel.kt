package com.example.fetch.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetch.data.Photo
import com.example.fetch.database.dao.PhotoDao
import com.example.fetch.database.event.PhotoEvent
import com.example.fetch.database.sort.SortType
import com.example.fetch.database.state.PhotoState
import com.example.fetch.repository.PhotoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PhotoViewModel(
    private val imageRepository: PhotoRepository,
    private val dao: PhotoDao,
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.Url)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _items = _sortType
        .flatMapConcat { sortType ->
            when(sortType) {
                SortType.Url -> dao.getPhotosByURL()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(PhotoState())
    val state = combine(_state, _sortType, _items) {state, sortType, photos ->
        state.copy(
            photos = photos,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PhotoState())

    fun onEvent(event: PhotoEvent) {
        when(event) {
            is PhotoEvent.DeletePhoto -> {
                viewModelScope.launch {
                    dao.deleteItem(event.photo)
                }
            }
            PhotoEvent.SavePhoto -> {
                val id = state.value.id
                val width = state.value.width
                val height = state.value.height
                val url = state.value.url
                val photographer = state.value.photographer
                val photographerUrl = state.value.photographerUrl
                val photographerId = state.value.photographerId
                val avgColor = state.value.avgColor
                val src = state.value.src
                val liked = state.value.liked
                val alt = state.value.alt

                if(url.isBlank() || photographer.isBlank()) {
                    return
                }

                val photo = Photo(
                    id = id,
                    width = width,
                    height = height,
                    url = url,
                    photographer = photographer,
                    photographerUrl = photographerUrl,
                    photographerId = photographerId,
                    avgColor = avgColor,
                    src = src,
                    liked = liked,
                    alt = alt
                )
                viewModelScope.launch {
                    dao.upsertPhoto(photo)
                }
                _state.update {it.copy(
                    isAddingPhoto = true,
                    id = 0,
                    width = 0,
                    height = 0,
                    url = "",
                    photographer = "",
                    photographerUrl = "",
                    photographerId = 0,
                    avgColor = "",
                    liked = false,
                    alt = ""
                )}
            }
            is PhotoEvent.SetId -> {
                _state.update { it.copy(
                    id = event.id
                )}
            }
            is PhotoEvent.SetWidth -> {
                _state.update { it.copy(
                    width = event.width
                )}
            }
            is PhotoEvent.SetHeight -> {
                _state.update { it.copy(
                    height = event.height
                )}
            }
            is PhotoEvent.SetUrl -> {
                _state.update { it.copy(
                    url = event.url
                )}
            }
            is PhotoEvent.SetPhotographer -> {
                _state.update { it.copy(
                    photographer = event.photographer
                )}
            }
            is PhotoEvent.SetPhotographerUrl -> {
                _state.update { it.copy(
                    photographerUrl = event.photographerUrl
                )}
            }
            is PhotoEvent.SetPhotographerId -> {
                _state.update { it.copy(
                    photographerId = event.photographerId
                )}
            }
            is PhotoEvent.SetAvgColor -> {
                _state.update { it.copy(
                    avgColor = event.avgColor
                )}
            }
            is PhotoEvent.SetSource -> {
                _state.update { it.copy(
                    src = event.source
                )}
            }
            is PhotoEvent.SetLiked -> {
                _state.update { it.copy(
                    liked = event.liked
                )}
            }
            is PhotoEvent.SetAlt -> {
                _state.update { it.copy(
                    alt = event.alt
                )}
            }
            is PhotoEvent.SortPhotos -> {
                _sortType.value = event.sortType
            }
        }
    }

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