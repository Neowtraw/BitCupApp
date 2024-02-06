package com.codingub.bitcupapp.domain.use_cases

import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsBookmarkPhoto @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(id: Long): Boolean {
        return repository.isBookmarkPhoto(id)
    }
}

class UpdateBookmarkPhoto @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(photo: Photo) {
        return repository.updateBookmarkPhoto(photo)
    }
}


class GetBookmarkPhotos @Inject constructor(
    private val repository: AppRepository
) {

    operator fun invoke(): Flow<List<Photo>> {
        return repository.getBookmarkPhotos()
    }
}
