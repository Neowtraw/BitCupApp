package com.codingub.bitcupapp.domain.use_cases

import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetFeaturedCollections @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(): ResultState<List<FeaturedCollection>> {
        return repository.getFeaturedCollections()
    }
}