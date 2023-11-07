package com.codingub.bitcupapp.di

import com.codingub.bitcupapp.BuildConfig
import com.codingub.bitcupapp.common.Constants
import com.codingub.bitcupapp.data.remote.AppApi
import com.codingub.bitcupapp.data.utils.AppNetworking
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    //provide endpoint from BuildConfig
    @Provides
    @Named(Constants.Injection.ENDPOINT)
    fun providesAppEndpoint() = BuildConfig.app_endpoint


    //provide api
    @Provides
    @Singleton
    fun provideHistoryAppService(networking: AppNetworking): AppApi =
        networking.historyAppApi()
}