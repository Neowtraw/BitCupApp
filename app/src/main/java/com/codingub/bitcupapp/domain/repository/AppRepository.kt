package com.codingub.bitcupapp.domain.repository

import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun insertBookmarkPhoto(photo: Photo)
    suspend fun deleteBookmarkPhoto(photoId: Long)

    fun getFeaturedCollections() : Flow<ResultState<List<FeaturedCollection>>>
    fun getLastCuratedPhotos() : Flow<ResultState<List<Photo>>>
    fun getPhoto(id: Long) : Flow<Photo>
    fun getBookmarkPhotos(id: Long) : Flow<List<Photo>>

    suspend fun searchPhotos(query: String) : ResultState<List<Photo>>

    fun initCacheUpdater()
    suspend fun updateCachedFeaturedCollections()
    suspend fun updateCachedCuratedPhotos()

}