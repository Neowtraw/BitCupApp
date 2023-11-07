package com.codingub.bitcupapp.di

import com.codingub.bitcupapp.App
import com.codingub.bitcupapp.BuildConfig
import com.codingub.bitcupapp.common.Constants.Injection.BUILD_VERSION_CODE
import com.codingub.bitcupapp.common.Constants.Injection.BUILD_VERSION_NAME
import com.codingub.bitcupapp.common.Constants.Injection.IS_DEBUG
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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


}