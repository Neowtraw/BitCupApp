package com.codingub.bitcupapp.di

import com.codingub.bitcupapp.data.local.datasource.LocalDataSource
import com.codingub.bitcupapp.data.local.datasource.LocalDataSourceImpl
import com.codingub.bitcupapp.data.remote.datasource.RemoteDataSource
import com.codingub.bitcupapp.data.remote.datasource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: LocalDataSourceImpl): LocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource
}