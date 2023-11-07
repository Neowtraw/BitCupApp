package com.codingub.bitcupapp.data.remote.models

data class FeaturedCollectionDto(
    val id: String,
    val title: String,
    val description: String?,
    val private: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videoCount: Int
)