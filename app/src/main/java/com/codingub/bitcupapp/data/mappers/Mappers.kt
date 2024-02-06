package com.codingub.bitcupapp.data.mappers

import com.codingub.bitcupapp.data.local.models.CuratedPhotoEntity
import com.codingub.bitcupapp.data.remote.models.CuratedPhotoDto
import com.codingub.bitcupapp.data.remote.models.FeaturedCollectionDto
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo


/**
 * Local
 */
fun CuratedPhotoEntity.toPhoto() = Photo(
    id = id,
    width = width,
    height = height,
    photoUrl = photoUrl,
    photographer = photographer,
    photographerUrl = photographerUrl,
    photographerId = photographerId,
    avgColor = avgColor,
    photoSrc = photoSrc,
    alt = alt,
)

fun Photo.toCuratedPhotoEntity() = CuratedPhotoEntity(
    id = id,
    width = width,
    height = height,
    photoUrl = photoUrl,
    photographer = photographer,
    photographerUrl = photographerUrl,
    photographerId = photographerId,
    avgColor = avgColor,
    photoSrc = photoSrc,
    alt = alt,
)

/**
 * Remote
 */
fun CuratedPhotoDto.toPhoto() = Photo(
    id = id,
    width = width,
    height = height,
    photoUrl = url,
    photographer = photographer,
    photographerUrl = photographerUrl,
    photographerId = photographerId,
    avgColor = avgColor,
    photoSrc = src,
    alt = alt
)

fun FeaturedCollectionDto.toFeatureCollection() = FeaturedCollection(
    id = id,
    title = title,
    description = description,
    collectionPrivate = private,
    mediaCount = mediaCount,
    photosCount = photosCount,
    videoCount = photosCount,
)
