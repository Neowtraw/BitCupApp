package com.codingub.bitcupapp.domain.use_cases

import com.codingub.bitcupapp.domain.repository.AppRepository
import javax.inject.Inject


class InitCacheUpdater @Inject constructor(
    private val repository: AppRepository
) {

    operator fun invoke() {
        return repository.initCacheUpdater()
    }
}

class UpdateCachedFeaturedCollections @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(){
        return repository.clearCachedFeaturedCollections()
    }
}

class UpdateCachedCuratedPhotos @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(){
        return repository.clearCachedCuratedPhotos()
    }
}

