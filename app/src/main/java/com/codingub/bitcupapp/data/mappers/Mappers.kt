package com.codingub.bitcupapp.data.mappers

import com.codingub.bitcupapp.data.local.models.BookmarkPhotoEntity
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
    id = id,
    width = width,
    height = height,
    photoUrl = photoUrl,
    photographer = photographer,
    photographerUrl = photographerUrl,
    photographerId = photographerId,
    avgColor = avgColor,
    photoSrc = photoSrc,
    liked = liked,
    alt = alt,
)

fun FeaturedCollectionEntity.toFeatureCollection() = FeaturedCollection(
    id = id,
    title = title,
    description = description,
    collectionPrivate = collectionPrivate,
    mediaCount = mediaCount,
    photosCount = photosCount,
    videoCount = photosCount
)

fun BookmarkPhotoEntity.toPhoto() = Photo(
    id = id,
    width = width,
    height = height,
    photoUrl = photoUrl,
    photographer = photographer,
    photographerUrl = photographerUrl,
    photographerId = photographerId,
    avgColor = avgColor,
    photoSrc = photoSrc,
    liked = liked,
    alt = alt
)

fun Photo.toBookmarkPhotoEntity() = BookmarkPhotoEntity(
    id = id,
    width = width,
    height = height,
    photoUrl = photoUrl,
    photographer = photographer,
    photographerUrl = photographerUrl,
    photographerId = photographerId,
    avgColor = avgColor,
    photoSrc = photoSrc,
    liked = liked,
    alt = alt
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
    liked = liked,
    alt = alt,
)

fun FeaturedCollection.toFeaturedCollectionEntity() = FeaturedCollectionEntity(
    id = id,
    title = title,
    description = description,
    collectionPrivate = collectionPrivate,
    mediaCount = mediaCount,
    photosCount = photosCount,
    videoCount = photosCount,
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
    liked = liked,
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
