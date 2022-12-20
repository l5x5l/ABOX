package com.strayalpaca.android.domain.usecase

import com.strayalpaca.android.domain.model.AvsB
import com.strayalpaca.android.domain.repository.AvsBRepository
import javax.inject.Inject

class UseCaseGetAvsBList @Inject constructor(
    private val avsBRepository: AvsBRepository
) {
    suspend operator fun invoke(pageIdx : Int, pageSize : Int = 10) : List<AvsB> {
        return avsBRepository.getAvsBList(pageIdx = pageIdx, pageSize)
    }

    suspend fun getBookmark(pageIdx : Int, pageSize : Int = 10) : List<AvsB> {
        return avsBRepository.getBookmarkAvsBList(pageIdx = pageIdx, pageSize = pageSize)
    }
}