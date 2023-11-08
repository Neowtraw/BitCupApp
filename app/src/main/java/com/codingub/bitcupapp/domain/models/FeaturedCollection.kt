package com.codingub.bitcupapp.domain.models

data class FeaturedCollection(
    val id: String,
    val title: String,
    val description: String?,
    val collectionPrivate: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videoCount: Int,
    val curatedPhotos: List<Photo> = emptyList()
)