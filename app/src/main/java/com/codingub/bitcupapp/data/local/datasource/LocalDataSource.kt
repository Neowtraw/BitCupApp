package com.codingub.bitcupapp.data.local.datasource

import com.codingub.bitcupapp.domain.models.Photo
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun updateBookmarkPhoto(photo: Photo)
    suspend fun getBookmarkPhoto(id: Long): Photo?
    fun getBookmarkPhotos(): Flow<List<Photo>>
}