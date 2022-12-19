package com.strayalpaca.android.domain.repository

import com.strayalpaca.android.domain.model.OXQuizCategory
import com.strayalpaca.android.domain.model.OXQuizItem

interface OxQuizRepository {
    suspend fun getOxQuizList(categoryIndex : Int) : List<OXQuizItem>

    suspend fun getOxQuizCategory() : List<OXQuizCategory>

    suspend fun getRandomOxQuizCategory() : OXQuizCategory
}