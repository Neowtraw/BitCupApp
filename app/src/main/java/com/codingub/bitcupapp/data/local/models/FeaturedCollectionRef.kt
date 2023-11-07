package com.codingub.bitcupapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FeaturedCollection")
data class FeaturedCollectionRef(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val collectionPrivate: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videoCount: Int
)