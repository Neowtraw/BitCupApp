package com.codingub.bitcupapp.domain.use_cases

import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.domain.repository.AppRepository
import javax.inject.Inject


class GetLastCuratedPhotos @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): ResultState<List<Photo>> = repository.getLastCuratedPhotos()
}

class GetPhoto @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(id: Long, isRemote: Boolean): ResultState<Photo> = repository.getPhoto(id = id, isRemote = isRemote)
}

class SearchPhotos @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(query: String): ResultState<List<Photo>> =
        repository.searchPhotos(query)
}