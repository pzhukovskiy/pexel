package com.example.fetch.database.state

import com.example.fetch.data.Photo
import com.example.fetch.data.Source
import com.example.fetch.database.sort.SortType

data class PhotoState(
    val photos: List<Photo> = emptyList(),
    val id: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val url: String = "",
    val photographer: String = "",
    val photographerUrl: String = "",
    val photographerId: Int = 0,
    val avgColor: String = "",
    val src: Source = Source("", "", "", "", "", "", "", ""),
    val liked: Boolean = false,
    val alt: String = "",
    val isAddingPhoto: Boolean = false,
    val sortType: SortType = SortType.Url
)