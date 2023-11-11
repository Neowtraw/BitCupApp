package com.codingub.bitcupapp.domain.use_cases

import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetLastCuratedPhotos @Inject constructor(
    private val repository: AppRepository
) {

    operator fun invoke(): Flow<ResultState<List<Photo>>> {
        return repository.getLastCuratedPhotos()
    }
}

class GetPhoto @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(id: Long): ResultState<Photo> {
        return repository.getPhoto(id = id)
    }
}

class SearchPhotos @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(query: String): ResultState<List<Photo>> {
        return repository.searchPhotos(query)
    }
}