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
    id = this.id,
    width = this.width,
    height = this.height,
    photoUrl = this.photoUrl,
    photographer = this.photographer,
    photographerUrl = this.photographerUrl,
    photographerId = this.photographerId,
    avgColor = this.avgColor,
    photoSrc = this.photoSrc,
    liked = this.liked,
    alt = this.alt,
)

fun FeaturedCollectionEntity.toFeatureCollection() = FeaturedCollection(
    id = this.id,
    title = this.title,
    description = this.description,
    collectionPrivate = this.collectionPrivate,
    mediaCount = this.mediaCount,
    photosCount = this.photosCount,
    videoCount = this.photosCount
)

fun BookmarkPhotoEntity.toPhoto() = Photo(
    id = this.id,
    width = this.width,
    height = this.height,
    photoUrl = this.photoUrl,
    photographer = this.photographer,
    photographerUrl = this.photographerUrl,
    photographerId = this.photographerId,
    avgColor = this.avgColor,
    photoSrc = this.photoSrc,
    liked = this.liked,
    alt = this.alt
)

fun Photo.toBookmarkPhotoEntity() = BookmarkPhotoEntity(
    id = this.id,
    width = this.width,
    height = this.height,
    photoUrl = this.photoUrl,
    photographer = this.photographer,
    photographerUrl = this.photographerUrl,
    photographerId = this.photographerId,
    avgColor = this.avgColor,
    photoSrc = this.photoSrc,
    liked = this.liked,
    alt = this.alt
)

fun Photo.toCuratedPhotoEntity() = CuratedPhotoEntity(
    id = this.id,
    width = this.width,
    height = this.height,
    photoUrl = this.photoUrl,
    photographer = this.photographer,
    photographerUrl = this.photographerUrl,
    photographerId = this.photographerId,
    avgColor = this.avgColor,
    photoSrc = this.photoSrc,
    liked = this.liked,
    alt = this.alt,
)

fun FeaturedCollection.toFeaturedCollectionEntity() = FeaturedCollectionEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        collectionPrivate = this.collectionPrivate,
        mediaCount = this.mediaCount,
        photosCount = this.photosCount,
        videoCount = this.photosCount,
)

/**
 * Remote
 */
fun CuratedPhotoDto.toPhoto() = Photo(
    id = this.id,
    width = this.width,
    height = this.height,
    photoUrl = this.url,
    photographer = this.photographer,
    photographerUrl = this.photographerUrl,
    photographerId = this.photographerId,
    avgColor = this.avgColor,
    photoSrc = this.src,
    liked = this.liked,
    alt = this.alt
)

fun FeaturedCollectionDto.toFeatureCollection() = FeaturedCollection(
    id = this.id,
    title = this.title,
    description = this.description,
    collectionPrivate = this.private,
    mediaCount = this.mediaCount,
    photosCount = this.photosCount,
    videoCount = this.photosCount,
)

fun Photo.toCuratedPhotoDto() = CuratedPhotoDto(
    id = this.id,
    width = this.width,
    height = this.height,
    url = this.photoUrl,
    photographer = this.photographer,
    photographerUrl = this.photographerUrl,
    photographerId = this.photographerId,
    avgColor = this.avgColor,
    src = this.photoSrc,
    liked = this.liked,
    alt = this.alt
)

fun FeaturedCollection.toFeaturedCollectionDto() = FeaturedCollectionDto(
    id = this.id,
    title = this.title,
    description = this.description,
    private = this.collectionPrivate,
    mediaCount = this.mediaCount,
    photosCount = this.photosCount,
    videoCount = this.photosCount
)