package com.codingub.bitcupapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codingub.bitcupapp.data.local.models.CuratedPhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkPhoto(photo: CuratedPhotoEntity)

    @Query("DELETE FROM CuratedPhoto WHERE id = :id")
    suspend fun deleteBookmarkPhoto(id: Long)

    @Query("SELECT * FROM CuratedPhoto WHERE id = :id")
    suspend fun getBookmarkPhoto(id: Long): CuratedPhotoEntity?

    @Query("SELECT * FROM CuratedPhoto")
    fun getBookmarkPhotos(): Flow<List<CuratedPhotoEntity>>
}