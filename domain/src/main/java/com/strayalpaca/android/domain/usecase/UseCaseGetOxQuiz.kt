package com.strayalpaca.android.domain.usecase

import com.strayalpaca.android.domain.model.OXQuizItem
import com.strayalpaca.android.domain.repository.OxQuizRepository
import javax.inject.Inject

class UseCaseGetOxQuiz @Inject constructor(
    private val oxQuizTestRepository: OxQuizRepository
) {
    suspend operator fun invoke(oxQuizCategoryIndex : Int) : List<OXQuizItem> {
        return oxQuizTestRepository.getOxQuizList(categoryIndex = oxQuizCategoryIndex)
    }
}
