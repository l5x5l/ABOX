package com.strayalpaca.android.domain.usecase

import com.strayalpaca.android.domain.model.HomeData
import com.strayalpaca.android.domain.repository.HomeRepository
import javax.inject.Inject

class UseCaseGetHomeData @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() : HomeData {
        return homeRepository.getHomeData()
    }
}