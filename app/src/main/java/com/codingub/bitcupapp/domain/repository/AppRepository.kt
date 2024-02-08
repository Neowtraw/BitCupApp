package com.codingub.bitcupapp.domain.repository

import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun updateBookmarkPhoto(photo: Photo)

    suspend fun getFeaturedCollections(): ResultState<List<FeaturedCollection>>
    suspend fun getLastCuratedPhotos(): ResultState<List<Photo>>
    suspend fun getPhoto(id: Long, isRemote: Boolean): ResultState<Photo>
    fun getBookmarkPhotos(): Flow<List<Photo>>
    suspend fun isBookmarkPhoto(id: Long): Boolean

    suspend fun searchPhotos(query: String): ResultState<List<Photo>>
}