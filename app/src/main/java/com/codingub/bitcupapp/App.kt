package com.codingub.bitcupapp

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import androidx.work.Configuration
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    init {
        Instance = this
    }

    companion object {
        private var Instance: App? = null
        fun getInstance(): App = Instance!!
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()
}