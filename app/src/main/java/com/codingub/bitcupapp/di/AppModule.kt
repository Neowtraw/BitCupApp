package com.codingub.bitcupapp.di

import com.codingub.bitcupapp.App
import com.codingub.bitcupapp.BuildConfig
import com.codingub.bitcupapp.common.Constants
import com.codingub.bitcupapp.common.Constants.Injection.BUILD_VERSION_CODE
import com.codingub.bitcupapp.common.Constants.Injection.BUILD_VERSION_NAME
import com.codingub.bitcupapp.common.Constants.Injection.IS_DEBUG
import com.codingub.bitcupapp.data.local.AppDatabase
import com.codingub.bitcupapp.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApp(): App {
        return App.getInstance()
    }

    @Provides
    @Named(Constants.Injection.IS_DEBUG)
    fun providesIsDebug(): Boolean = BuildConfig.DEBUG

    @Provides
    @Named(Constants.Injection.BUILD_VERSION_CODE)
    fun providesBuildVersionCode() = BuildConfig.VERSION_CODE

    @Provides
    @Named(Constants.Injection.BUILD_VERSION_NAME)
    fun providesBuildVersionName() = BuildConfig.VERSION_NAME

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

}