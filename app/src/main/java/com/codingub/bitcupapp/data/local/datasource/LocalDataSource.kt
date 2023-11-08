package com.codingub.bitcupapp.data.local.datasource

import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertFeaturedCollections(collections: List<FeaturedCollection>)
    suspend fun insertCuratedPhotos(photos: List<Photo>)
    suspend fun insertBookmarkPhoto(photo: Photo)

    suspend fun deleteBookmarkPhoto(photoId: Long)

    fun getFeaturedCollections() : Flow<List<FeaturedCollection>>
    fun getLastCuratedPhotos() : Flow<List<Photo>>
    fun getPhoto(id: Long) : Flow<Photo>
    fun getBookmarkPhotos(id: Long) : Flow<List<Photo>>


}