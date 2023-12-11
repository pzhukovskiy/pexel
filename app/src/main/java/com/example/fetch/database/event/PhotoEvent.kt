package com.example.fetch.database.event

import com.example.fetch.data.Photo
import com.example.fetch.data.Source
import com.example.fetch.database.sort.SortType

sealed interface PhotoEvent {
    object SavePhoto: PhotoEvent
    data class SetId(val id: Int): PhotoEvent
    data class SetWidth(val width: Int): PhotoEvent
    data class SetHeight(val height: Int): PhotoEvent
    data class SetUrl(val url: String): PhotoEvent
    data class SetPhotographer(val photographer: String): PhotoEvent
    data class SetPhotographerUrl(val photographerUrl: String): PhotoEvent
    data class SetPhotographerId(val photographerId: Int): PhotoEvent
    data class SetAvgColor(val avgColor: String): PhotoEvent
    data class SetSource(val source: Source): PhotoEvent
    data class SetLiked(val liked: Boolean): PhotoEvent
    data class SetAlt(val alt: String): PhotoEvent
    data class SortPhotos(val sortType: SortType): PhotoEvent
    data class DeletePhoto(val photo: Photo): PhotoEvent
}