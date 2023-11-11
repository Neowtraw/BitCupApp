package com.codingub.bitcupapp.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.codingub.bitcupapp.data.worker.util.WorkerConstants
import com.codingub.bitcupapp.domain.repository.AppRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltWorker
class CacheUpdateWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: AppRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            Log.e("CacheUpdateWorker", "Success")

            repository.clearCachedCuratedPhotos()
            repository.clearCachedFeaturedCollections()
            Result.success(workDataOf(WorkerConstants.CACHE_UPDATE_KEY to true))
        }catch (ex: Exception) {
            Result.failure(workDataOf(WorkerConstants.CACHE_UPDATE_KEY to false))
        }
    }
}