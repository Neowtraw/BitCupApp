package com.codingub.bitcupapp.data.local.datasource

import com.codingub.bitcupapp.domain.models.Photo
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun updateBookmarkPhoto(photo: Photo)
    fun getBookmarkPhoto(id: Long): Flow<Photo?>
    fun getBookmarkPhotos(): Flow<List<Photo>>
}