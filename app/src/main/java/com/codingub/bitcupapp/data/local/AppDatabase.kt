package com.codingub.bitcupapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codingub.bitcupapp.data.local.models.CuratedPhotoEntity


@Database(
    entities = [CuratedPhotoEntity::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

}