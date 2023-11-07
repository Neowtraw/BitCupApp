package com.codingub.bitcupapp.data.mappers

import com.codingub.bitcupapp.data.local.models.CuratedPhotoEntity
import com.codingub.bitcupapp.data.local.models.FeaturedCollectionEntity
import com.codingub.bitcupapp.data.remote.models.CuratedPhotoDto
import com.codingub.bitcupapp.data.remote.models.FeaturedCollectionDto
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo


/**
 * Local
 */
fun CuratedPhotoEntity.toPhoto() = Photo(
    id = this.id,
    width = this.width,
    height = this.height,
    src = this.photoSrc,
    photographer = this.photographer
)

fun FeaturedCollectionEntity.toFeatureCollection() = FeaturedCollection(
    id = this.id,
    title = this.title
)

/**
 * Remote
 */
fun CuratedPhotoDto.toPhoto() = Photo(
    id = this.id,
    width = this.width,
    height = this.height,
    src = this.src,
    photographer = this.photographer
)

fun FeaturedCollectionDto.toFeatureCollection() = FeaturedCollection(
    id = this.id,
    title = this.title
)