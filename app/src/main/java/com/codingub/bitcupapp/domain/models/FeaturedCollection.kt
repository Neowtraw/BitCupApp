package com.codingub.bitcupapp.domain.models

data class FeaturedCollection(
    val id: String,
    val title: String,
    val curatedPhotos: List<Photo> = emptyList()
)