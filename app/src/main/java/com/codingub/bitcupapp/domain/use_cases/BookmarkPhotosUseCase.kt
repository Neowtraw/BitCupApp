package com.codingub.bitcupapp.domain.use_cases

import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class InsertBookmarkPhoto @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(photo: Photo) {
        return repository.insertBookmarkPhoto(photo)
    }
}

class DeleteBookmarkPhoto @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(photoId: Long) {
        return repository.deleteBookmarkPhoto(photoId)
    }
}

class GetBookmarkPhotos @Inject constructor(
    private val repository: AppRepository
) {

    operator fun invoke() : Flow<List<Photo>> {
        return repository.getBookmarkPhotos()
    }
}
