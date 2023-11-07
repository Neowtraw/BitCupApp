package com.codingub.bitcupapp.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.codingub.bitcupapp.data.worker.util.WorkerConstants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class CacheUpdateWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    //repos
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        return try {

            //rep work for caching
            Result.success(workDataOf(WorkerConstants.CACHE_UPDATE_KEY to true))
        }catch (ex: Exception) {
            Result.failure(workDataOf(WorkerConstants.CACHE_UPDATE_KEY to false))
        }
    }
}