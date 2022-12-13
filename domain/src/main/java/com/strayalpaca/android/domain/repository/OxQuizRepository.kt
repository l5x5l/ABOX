package com.strayalpaca.android.domain.repository

import com.strayalpaca.android.domain.model.OXQuizItem

interface OxQuizRepository {
    suspend fun getOxQuizList(categoryIndex : Int) : List<OXQuizItem>
}