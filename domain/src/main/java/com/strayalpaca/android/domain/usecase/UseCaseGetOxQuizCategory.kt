package com.strayalpaca.android.domain.usecase

import com.strayalpaca.android.domain.model.OXQuizCategory
import com.strayalpaca.android.domain.repository.OxQuizRepository
import javax.inject.Inject

class UseCaseGetOxQuizCategory @Inject constructor(
    private val repository: OxQuizRepository
) {
    suspend operator fun invoke() : List<OXQuizCategory> {
        return repository.getOxQuizCategory()
    }
}