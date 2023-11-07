package com.codingub.bitcupapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codingub.bitcupapp.data.local.models.CuratedPhotoEntity
import com.codingub.bitcupapp.data.local.models.FeaturedCollectionEntity


@Database(
entities = [FeaturedCollectionEntity::class, CuratedPhotoEntity::class],
    version = 1
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase(){

    abstract class dao: AppDao

}