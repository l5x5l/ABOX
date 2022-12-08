package com.strayalpaca.android.domain.repository

import com.strayalpaca.android.domain.model.AvsB

interface AvsBRepository {
    suspend fun getAvsBList(pageIdx : Int, pageSize : Int = 10) : List<AvsB>
    suspend fun toggleLike(AvsBIndex : Int, isLike : Boolean) : Boolean
    suspend fun getAvsB(AvsBIndex : Int) : AvsB
}