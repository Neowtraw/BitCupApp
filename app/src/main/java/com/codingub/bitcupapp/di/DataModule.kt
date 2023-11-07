package com.codingub.bitcupapp.di

import android.content.Context
import androidx.room.Room
import com.codingub.bitcupapp.BuildConfig
import com.codingub.bitcupapp.common.Constants
import com.codingub.bitcupapp.data.local.AppDatabase
import com.codingub.bitcupapp.data.remote.AppApi
import com.codingub.bitcupapp.data.utils.AppNetworking
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Named(Constants.Injection.IS_DEBUG)
    fun providesIsDebug(): Boolean = BuildConfig.DEBUG

    @Provides
    @Named(Constants.Injection.BUILD_VERSION_CODE)
    fun providesBuildVersionCode() = BuildConfig.VERSION_CODE

    @Provides
    @Named(Constants.Injection.BUILD_VERSION_NAME)
    fun providesBuildVersionName() = BuildConfig.VERSION_NAME

}