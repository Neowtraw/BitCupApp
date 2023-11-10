package com.codingub.bitcupapp.data.local.datasource

import androidx.room.withTransaction
import com.codingub.bitcupapp.data.local.AppDao
import com.codingub.bitcupapp.data.local.AppDatabase
import com.codingub.bitcupapp.data.mappers.toBookmarkPhotoEntity
import com.codingub.bitcupapp.data.mappers.toCuratedPhotoEntity
import com.codingub.bitcupapp.data.mappers.toFeatureCollection
import com.codingub.bitcupapp.data.mappers.toFeaturedCollectionEntity
import com.codingub.bitcupapp.data.mappers.toPhoto
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.utils.extension.isEmptyOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val database: AppDatabase,
    private val dao: AppDao
) : LocalDataSource {

    override suspend fun insertFeaturedCollections(collections: List<FeaturedCollection>) {
        return database.withTransaction {
            dao.deleteAllFeaturedCollections()
            dao.insertFeaturedCollections(collections.map { it.toFeaturedCollectionEntity() })
        }
    }

    override suspend fun insertCuratedPhotos(photos: List<Photo>) {
        return database.withTransaction {
            dao.deleteAllCuratedPhotos()
            dao.insertCuratedPhotos(photos = photos.map { it.toCuratedPhotoEntity() })
        }
    }

    override fun getPhoto(id: Long): Flow<Photo> {
        return dao.getPhoto(id).map { it.toPhoto() }
    }

    override fun getBookmarkPhotos() : Flow<List<Photo>> {
        return dao.getBookmarkPhotos().map {
            it.map { collection -> collection.toPhoto() }
        }
    }

    override fun getFeaturedCollections(): Flow<List<FeaturedCollection>> {
        return dao.getFeaturedCollections().map { it.map { collection -> collection.toFeatureCollection() } }
    }

    override fun getLastCuratedPhotos(): Flow<List<Photo>> {
        return dao.getLastCuratedPhotos().map { it.map { photo -> photo.toPhoto() } }
    }


    override suspend fun getBookmarkPhoto(id: Long): Photo? {
        return dao.getBookmarkPhoto(id)?.toPhoto()
    }

    override suspend fun updateBookmarkPhoto(photo: Photo) {
        val existingPhoto = getBookmarkPhoto(photo.id)

        if (existingPhoto != null) {
            dao.deleteBookmarkPhoto(photo.id)
            return
        }

        dao.insertBookmarkPhoto(photo.toBookmarkPhotoEntity())
    }

    override suspend fun clearCachedCuratedPhotos() {
        dao.deleteAllCuratedPhotos()
    }

    override suspend fun clearCachedFeaturedCollections() {
        dao.deleteAllFeaturedCollections()
    }
}