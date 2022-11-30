package com.strayalpaca.android.data.repositoryImpl

import com.strayalpaca.android.domain.model.AvsB
import com.strayalpaca.android.domain.repository.AvsBRepository

class AvsBTestRepository : AvsBRepository {
    override suspend fun getAvsBList(pageIdx: Int, pageSize: Int): List<AvsB> {
        val temp = mutableListOf<AvsB>()
        for(i : Int in 1..pageSize) {
            temp.add(AvsB(index = pageIdx * pageSize, title = "${pageIdx * pageSize + i}번째 post", updateDate = "", thumbnailUrl = "https://picsum.photos/seed/picsum/500/500", isLike = false, primaryColor = "#000000"))
        }
        return temp
    }

    override suspend fun toggleLike(AvsBIndex: Int, isLike: Boolean): Boolean {
        return !isLike
    }
}