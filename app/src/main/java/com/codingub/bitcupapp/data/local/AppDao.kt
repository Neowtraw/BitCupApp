package com.codingub.bitcupapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codingub.bitcupapp.data.local.models.BookmarkPhotoEntity
import com.codingub.bitcupapp.data.local.models.CuratedPhotoEntity
import com.codingub.bitcupapp.data.local.models.FeaturedCollectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeaturedCollections(collections: List<FeaturedCollectionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCuratedPhotos(photos: List<CuratedPhotoEntity>)

    @Insert
    suspend fun insertBookmarkPhoto(photo: BookmarkPhotoEntity)

    @Query("SELECT * FROM BookmarkPhoto")
    fun getBookmarkPhotos(): Flow<List<BookmarkPhotoEntity>>

    @Query("SELECT * FROM BookmarkPhoto WHERE id = :id")
    suspend fun getBookmarkPhoto(id: Long): BookmarkPhotoEntity?

    @Query("SELECT * FROM FeaturedCollection")
    fun getFeaturedCollections() : Flow<List<FeaturedCollectionEntity>>

    @Query("SELECT * FROM CuratedPhoto")
    fun getLastCuratedPhotos() : Flow<List<CuratedPhotoEntity>>

    @Query("SELECT * FROM CuratedPhoto WHERE id = :id")
    fun getPhoto(id: Long) : Flow<CuratedPhotoEntity>

    @Query("DELETE FROM featuredcollection")
    suspend fun deleteAllFeaturedCollections()

    @Query("DELETE FROM curatedphoto")
    suspend fun deleteAllCuratedPhotos()

    @Query("DELETE FROM bookmarkphoto WHERE id = :photoId")
    suspend fun deleteBookmarkPhoto(photoId: Long)

}