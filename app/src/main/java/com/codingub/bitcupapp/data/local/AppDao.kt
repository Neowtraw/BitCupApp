package com.codingub.bitcupapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codingub.bitcupapp.data.local.models.CuratedPhotoEntity
import com.codingub.bitcupapp.data.local.models.FeaturedCollectionRef
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeaturedCollections(collections: List<FeaturedCollectionRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCuratedPhotos(photos: List<CuratedPhotoEntity>)

    @Query("SELECT * FROM FeaturedCollection")
    fun getFeaturedCollections() : Flow<List<FeaturedCollectionRef>>

    @Query("SELECT * FROM CuratedPhoto")
    fun getLastCuratedPhotos() : Flow<List<CuratedPhotoEntity>>

    @Query("SELECT * FROM CuratedPhoto WHERE id = :id")
    fun getPhoto(id: Int) : Flow<CuratedPhotoEntity>

    @Query("UPDATE CuratedPhoto SET favorite = 1 WHERE id = :id")
    fun setFavoritePhoto(id: Int)
}