package com.codingub.bitcupapp.data.repository

import android.util.Log
import com.codingub.bitcupapp.common.ConnectionManager
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.data.local.datasource.LocalDataSource
import com.codingub.bitcupapp.data.remote.datasource.RemoteDataSource
import com.codingub.bitcupapp.data.utils.NetworkUnavailableException
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.domain.repository.AppRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val connectionManager: ConnectionManager
) : AppRepository {

    private companion object {
        val TAG = AppRepositoryImpl::class.java.name
    }
    override suspend fun getFeaturedCollections(): ResultState<List<FeaturedCollection>> {
        return try {
            val collections = remoteDataSource.getFeaturedCollections()

            ResultState.Success(collections)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            if(!connectionManager.isConnected) return ResultState.Error(NetworkUnavailableException())

            Log.e(TAG, e.message.toString())
            ResultState.Error(e)
        }
    }

    override suspend fun getLastCuratedPhotos(): ResultState<List<Photo>> {
        return try {
            val photos = remoteDataSource.getCuratedPhotos()
            ResultState.Success(photos)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            if(!connectionManager.isConnected) return ResultState.Error(NetworkUnavailableException())


            Log.e(TAG, e.message.toString())
            ResultState.Error(e)
        }
    }

    override suspend fun searchPhotos(query: String): ResultState<List<Photo>> {
        return try {
            ResultState.Success(remoteDataSource.searchPhotos(query))
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            if(!connectionManager.isConnected) return ResultState.Error(NetworkUnavailableException())


            Log.e("searchPhotos", e.message.toString())
            ResultState.Error(e)
        }
    }

    override suspend fun getPhoto(id: Long, isRemote: Boolean): ResultState<Photo> {
        return try {
            if(isRemote) ResultState.Success(remoteDataSource.getPhoto(id))
            else ResultState.Success(localDataSource.getBookmarkPhoto(id)!!)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            if(!connectionManager.isConnected) return ResultState.Error(NetworkUnavailableException())

            Log.e(TAG, e.message.toString())
            ResultState.Error(e)
        }
    }

    override suspend fun updateBookmarkPhoto(photo: Photo) {
        return localDataSource.updateBookmarkPhoto(photo)
    }

    override suspend fun isBookmarkPhoto(id: Long): Boolean {
        return localDataSource.getBookmarkPhoto(id) != null
    }

    override fun getBookmarkPhotos(): Flow<List<Photo>> {
        return localDataSource.getBookmarkPhotos()
    }
}