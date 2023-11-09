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
import com.codingub.bitcupapp.data.local.models.BookmarkPhotoEntity
import com.codingub.bitcupapp.data.remote.datasource.RemoteDataSource
import com.codingub.bitcupapp.data.utils.NetworkBoundResultState
import com.codingub.bitcupapp.data.worker.CacheUpdateWorker
import com.codingub.bitcupapp.data.worker.util.WorkerConstants
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.domain.repository.AppRepository
import com.codingub.bitcupapp.utils.extension.isEmptyOrNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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
                Log.d("AppRepository", "Query")

                localDataSource.getFeaturedCollections()

            },
            shouldFetch = {
                it.isNullOrEmpty()
            },
            fetch = {
                Log.d("AppRepository", "Fetch")
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
        shouldFetch = {
            it.isNullOrEmpty()
        },
        fetch = {
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

    override suspend fun updateBookmarkPhoto(photo: Photo) {
        return localDataSource.updateBookmarkPhoto(photo)
    }

    override suspend fun getBookmarkPhoto(id: Long): Boolean {
        return localDataSource.getBookmarkPhoto(id) != null
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

    override fun getBookmarkPhotos(): Flow<List<Photo>> {
        return localDataSource.getBookmarkPhotos()
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
            Constants.UPDATE_INTERVAL, TimeUnit.HOURS
        )
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
        try {
            val collections = remoteDataSource.getFeaturedCollections()
            localDataSource.insertFeaturedCollections(collections)
            Log.e("updateCachedCollections", "Successfully")
        } catch (e: Exception) {
            Log.e("updateCachedCollections", e.message.toString())
            throw e
        }
    }

    override suspend fun updateCachedCuratedPhotos() {
        try {
            val photos = remoteDataSource.getCuratedPhotos()
            localDataSource.insertCuratedPhotos(photos)
            Log.e("updateCachedPhotos", "Successfully")
        } catch (e: Exception) {
            Log.e("updateCachedPhotos", e.message.toString())
            throw e
        }
    }
}