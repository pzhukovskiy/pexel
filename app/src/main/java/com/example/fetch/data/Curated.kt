package com.example.fetch.data

data class Curated(
    val page: Int,
    val perPage: Int,
    val photos: List<Photo>
)