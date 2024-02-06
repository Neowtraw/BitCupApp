package com.codingub.bitcupapp.data.local.datasource

import androidx.room.withTransaction
import com.codingub.bitcupapp.data.local.AppDao
import com.codingub.bitcupapp.data.local.AppDatabase
import com.codingub.bitcupapp.data.mappers.toCuratedPhotoEntity
import com.codingub.bitcupapp.data.mappers.toPhoto
import com.codingub.bitcupapp.domain.models.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val database: AppDatabase,
    private val dao: AppDao
) : LocalDataSource {

    override fun getBookmarkPhoto(id: Long): Flow<Photo?> =
        dao.getBookmarkPhoto(id = id).map { it?.toPhoto() }

    override fun getBookmarkPhotos(): Flow<List<Photo>> =
        dao.getBookmarkPhotos().map {
            it.map { collection -> collection.toPhoto() }

    }

    override suspend fun updateBookmarkPhoto(photo: Photo) {
        database.withTransaction {
            getBookmarkPhoto(photo.id).firstOrNull()?.let {
                dao.deleteBookmarkPhoto(photo.id)
                return@withTransaction
            }

            dao.insertBookmarkPhoto(photo.toCuratedPhotoEntity())
        }
    }
}