package com.strayalpaca.android.domain.repository

import com.strayalpaca.android.domain.model.AvsB

interface AvsBRepository {
    suspend fun getAvsBList(pageIdx : Int, pageSize : Int) : List<AvsB>
    suspend fun toggleLike(AvsBIndex : Int, isLike : Boolean) : Boolean
    suspend fun getAvsB(AvsBIndex : Int) : AvsB
    suspend fun getRandomAvsB() : AvsB
    suspend fun getBookmarkAvsBList(pageIdx : Int, pageSize : Int) : List<AvsB>
}