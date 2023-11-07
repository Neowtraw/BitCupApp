package com.codingub.bitcupapp.data.local.models

import androidx.room.Embedded
import androidx.room.Relation

data class FeaturedCollectionEntity(
    @Embedded val featureCollection: FeaturedCollectionRef,
    @Relation(
        parentColumn = "title",
        entityColumn = "id",
        entity = CuratedPhotoEntity::class
    )
    val photos: List<CuratedPhotoEntity>
)