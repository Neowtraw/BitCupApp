package com.codingub.bitcupapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class App : Application(){

    init{
        Instance = this

    }

    companion object {
        private var Instance: App? = null
        fun getInstance(): App = Instance!!
    }
}