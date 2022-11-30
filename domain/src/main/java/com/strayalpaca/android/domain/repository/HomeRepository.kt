package com.strayalpaca.android.domain.repository

import com.strayalpaca.android.domain.model.HomeData

interface HomeRepository {
    suspend fun getHomeData() : HomeData
}