package com.codingub.bitcupapp.data.repository

import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import com.codingub.bitcupapp.common.Constants
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.data.local.datasource.LocalDataSource
import com.codingub.bitcupapp.data.remote.datasource.RemoteDataSource
import com.codingub.bitcupapp.data.utils.NetworkBoundResultState
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.domain.repository.AppRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : AppRepository {

    override suspend fun getFeaturedCollections():ResultState<List<FeaturedCollection>> {
        return try {
            ResultState.Success(remoteDataSource.getFeaturedCollections())
        } catch (e: Exception) { // temporary
            if(e is CancellationException) throw e
            Log.e("getPhoto", e.message.toString())
            ResultState.Error(e)
        }
    }

    override suspend fun getLastCuratedPhotos(): ResultState<List<Photo>> {
        return try {
            ResultState.Success(remoteDataSource.getCuratedPhotos())
        } catch (e: Exception) { // temporary
            if(e is CancellationException) throw e
            Log.e("getPhoto", e.message.toString())
            ResultState.Error(e)
        }
    }

    override suspend fun searchPhotos(query: String): ResultState<List<Photo>> {
        return try {
            val data = remoteDataSource.searchPhotos(query)
            ResultState.Success(data)
        } catch (e: Exception) {
            if(e is CancellationException) throw e
            Log.e("searchPhotos", e.message.toString())
            ResultState.Error(e)
        }
    }

    override suspend fun getPhoto(id: Long): ResultState<Photo> {
        return try {
            val data = remoteDataSource.getPhoto(id)
            ResultState.Success(data)
        } catch (e: Exception) {
            Log.e("getPhoto", e.message.toString())
            ResultState.Error(e)
        }
    }

    override suspend fun updateBookmarkPhoto(photo: Photo) {
        return localDataSource.updateBookmarkPhoto(photo)
    }

    override suspend fun isBookmarkPhoto(id: Long): Boolean {
        return localDataSource.getBookmarkPhoto(id).firstOrNull() != null
    }

    override fun getBookmarkPhotos(): Flow<List<Photo>> {
        return localDataSource.getBookmarkPhotos()
    }
}