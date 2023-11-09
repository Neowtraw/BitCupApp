package com.codingub.bitcupapp.data.local.datasource

import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertFeaturedCollections(collections: List<FeaturedCollection>)
    suspend fun insertCuratedPhotos(photos: List<Photo>)

    suspend fun updateBookmarkPhoto(photo: Photo)

    fun getFeaturedCollections() : Flow<List<FeaturedCollection>>
    fun getLastCuratedPhotos() : Flow<List<Photo>>
    fun getPhoto(id: Long) : Flow<Photo>
    suspend fun getBookmarkPhoto(id: Long) : Photo?
    fun getBookmarkPhotos() : Flow<List<Photo>>


}