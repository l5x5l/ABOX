package com.strayalpaca.android.domain.usecase

import com.strayalpaca.android.domain.model.AvsB
import com.strayalpaca.android.domain.repository.AvsBRepository
import javax.inject.Inject

class UseCaseGetAvsB @Inject constructor(
    private val repository : AvsBRepository
) {
    suspend operator fun invoke(AvsBIndex: Int): AvsB {
        return repository.getAvsB(AvsBIndex)
    }
}