package com.codingub.bitcupapp.di

import com.codingub.bitcupapp.data.repository.AppRepositoryImpl
import com.codingub.bitcupapp.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(repository: AppRepositoryImpl) : AppRepository
}