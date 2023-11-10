package com.codingub.bitcupapp.domain.repository

import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun updateBookmarkPhoto(photo: Photo)

    fun getFeaturedCollections() : Flow<ResultState<List<FeaturedCollection>>>
    fun getLastCuratedPhotos() : Flow<ResultState<List<Photo>>>
    suspend fun getPhoto(id: Long) : ResultState<Photo>
    fun getBookmarkPhotos() : Flow<List<Photo>>
    suspend fun getBookmarkPhoto(id: Long) : Boolean

    suspend fun searchPhotos(query: String) : ResultState<List<Photo>>

    fun initCacheUpdater()
    suspend fun clearCachedFeaturedCollections()
    suspend fun clearCachedCuratedPhotos()

}