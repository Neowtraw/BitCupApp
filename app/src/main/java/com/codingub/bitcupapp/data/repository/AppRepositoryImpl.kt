package com.codingub.bitcupapp.data.repository

import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.codingub.bitcupapp.common.Constants
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.data.local.datasource.LocalDataSource
import com.codingub.bitcupapp.data.remote.datasource.RemoteDataSource
import com.codingub.bitcupapp.data.utils.NetworkBoundResultState
import com.codingub.bitcupapp.data.worker.CacheUpdateWorker
import com.codingub.bitcupapp.data.worker.util.WorkerConstants
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.domain.repository.AppRepository
import com.codingub.bitcupapp.utils.extension.isEmptyOrNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
    private val workManager: WorkManager
) : AppRepository {

    private val workConstraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresStorageNotLow(true)
        .setRequiresBatteryNotLow(true)
        .build()

    override fun getFeaturedCollections(): Flow<ResultState<List<FeaturedCollection>>> =
        NetworkBoundResultState(
            query = {
                localDataSource.getFeaturedCollections()
            },
            shouldFetch = { collections ->
                collections.isEmptyOrNull()
            },
            fetch = {
                remoteDataSource.getFeaturedCollections()
            },
            saveFetchResult = {
                localDataSource.insertFeaturedCollections(it)
                Log.d("AppRepositorySaved", it.toString())
            },
            onFetchFailed = {
                Log.d("AppRepositoryFailed", it.toString())
            },
            dispatcher = dispatcher
        )

    override fun getLastCuratedPhotos(): Flow<ResultState<List<Photo>>> = NetworkBoundResultState(
        query = {
            localDataSource.getLastCuratedPhotos()
        },
        shouldFetch = { photos ->
            photos.isEmptyOrNull()
        },
        fetch = {
            delay(2000)
            remoteDataSource.getCuratedPhotos()
        },
        saveFetchResult = {
            localDataSource.insertCuratedPhotos(it)
            Log.d("AppRepositorySaved", it.toString())
        },
        onFetchFailed = {
            Log.d("AppRepositoryFailed", it.toString())
        },
        dispatcher = dispatcher
    )

    override suspend fun insertBookmarkPhoto(photo: Photo) {
        return localDataSource.insertBookmarkPhoto(photo)
    }

    override suspend fun deleteBookmarkPhoto(photoId: Long) {
        return localDataSource.deleteBookmarkPhoto(photoId)
    }

    override fun getPhoto(id: Long): Flow<Photo> {
        return localDataSource.getPhoto(id)
    }

    override fun getBookmarkPhotos(id: Long): Flow<List<Photo>> {
        return localDataSource.getBookmarkPhotos(id)
    }

    override suspend fun searchPhotos(query: String): ResultState<List<Photo>> {
        return try {
            val data = remoteDataSource.searchPhotos(query)
            ResultState.Success(data)
        } catch (e: Exception) {
            Log.e("searchPhotos", e.message.toString())
            ResultState.Error(e)
        }
    }

    override fun initCacheUpdater() {
        val updateWorkRequest = PeriodicWorkRequestBuilder<CacheUpdateWorker>(
            Constants.UPDATE_INTERVAL, TimeUnit.MINUTES)
            .setConstraints(workConstraints)
            .addTag(WorkerConstants.WORKER_TAG)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WorkerConstants.WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            updateWorkRequest
        )
    }

    override suspend fun updateCachedFeaturedCollections() {
        try{
            val collections = remoteDataSource.getFeaturedCollections()
            localDataSource.insertFeaturedCollections(collections)
        } catch (e: Exception){
            Log.e("updateCachedCollections", e.message.toString())
            throw e
        }
    }

    override suspend fun updateCachedCuratedPhotos() {
        try {
            val photos = remoteDataSource.getCuratedPhotos()
            localDataSource.insertCuratedPhotos(photos)
        } catch (e: Exception) {
            Log.e("updateCachedCollections", e.message.toString())
            throw e
        }    }
}