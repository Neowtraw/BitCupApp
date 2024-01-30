package com.codingub.bitcupapp.di

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkManagerInitializer //: Initializer<WorkManager> {
{
    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context) = WorkManager.getInstance(context)

}

//    @Provides
//    @Singleton
//    override fun create(@ApplicationContext appContext: Context): WorkManager {
//        val workerFactory = getWorkerFactory(appContext)
//        val configuration = Configuration.Builder()
//            .setWorkerFactory(workerFactory)
//            .setMinimumLoggingLevel(android.util.Log.DEBUG)
//            .build()
//        WorkManager.initialize(appContext, configuration)
//        return WorkManager.getInstance(appContext)
//    }
//
//    override fun dependencies(): List<Class<out Initializer<*>>> {
//        return emptyList()
//    }
//
//    private fun getWorkerFactory(appContext: Context): HiltWorkerFactory {
//        val workManagerEntryPoint = EntryPointAccessors.fromApplication(
//            appContext,
//            WorkManagerInitializerEntryPoint::class.java
//        )
//        return workManagerEntryPoint.hiltWorkerFactory()
//    }
//
//    @InstallIn(SingletonComponent::class)
//    @EntryPoint
//    interface WorkManagerInitializerEntryPoint {
//        fun hiltWorkerFactory(): HiltWorkerFactory
//    }
//}