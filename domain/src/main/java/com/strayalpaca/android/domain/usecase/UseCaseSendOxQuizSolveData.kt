package com.strayalpaca.android.domain.usecase

import com.strayalpaca.android.domain.model.OXQuizItem
import com.strayalpaca.android.domain.repository.OxQuizRepository
import javax.inject.Inject

class UseCaseSendOxQuizSolveData @Inject constructor(private val repository : OxQuizRepository) {
    suspend operator fun invoke(solvedQuizData : List<OXQuizItem>) {
        repository.sendSolveData(solvedQuizData)
    }
}