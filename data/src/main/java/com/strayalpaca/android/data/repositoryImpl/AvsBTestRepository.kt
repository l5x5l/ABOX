package com.strayalpaca.android.data.repositoryImpl

import com.strayalpaca.android.domain.model.AvsB
import com.strayalpaca.android.domain.model.AvsBItem
import com.strayalpaca.android.domain.repository.AvsBRepository
import kotlin.random.Random

class AvsBTestRepository : AvsBRepository {
    override suspend fun getAvsBList(pageIdx: Int, pageSize: Int): List<AvsB> {
        val temp = mutableListOf<AvsB>()
        for (i: Int in 1..pageSize) {
            temp.add(
                AvsB(
                    index = pageIdx * pageSize + i,
                    title = "${pageIdx * pageSize + i}번째 post",
                    updateDate = "",
                    thumbnailUrl = "https://picsum.photos/seed/picsum/500/500",
                    isLike = false,
                    primaryColor = "#000000"
                )
            )
        }
        return temp
    }

    override suspend fun toggleLike(AvsBIndex: Int, isLike: Boolean): Boolean {
        return !isLike
    }

    override suspend fun getAvsB(AvsBIndex: Int): AvsB {
        return AvsB(
            index = AvsBIndex,
            title = "테스트 AvsB",
            primaryColor = "#ffffff",
            updateDate = "2022-12-07",
            thumbnailUrl = "",
            isLike = false,
            completeCount = 0,
            itemList = listOf(
                AvsBItem(index = 0, imageUrl = "https://picsum.photos/500/500?random=0", AvsBIndex = AvsBIndex),
                AvsBItem(index = 1, imageUrl = "https://picsum.photos/500/500?random=1", AvsBIndex = AvsBIndex),
                AvsBItem(index = 2, imageUrl = "https://picsum.photos/500/500?random=2", AvsBIndex = AvsBIndex),
                AvsBItem(index = 3, imageUrl = "https://picsum.photos/500/500?random=3", AvsBIndex = AvsBIndex),
                AvsBItem(index = 4, imageUrl = "https://picsum.photos/500/500?random=4", AvsBIndex = AvsBIndex),
                AvsBItem(index = 5, imageUrl = "https://picsum.photos/500/500?random=5", AvsBIndex = AvsBIndex),
                AvsBItem(index = 6, imageUrl = "https://picsum.photos/500/500?random=6", AvsBIndex = AvsBIndex),
                AvsBItem(index = 7, imageUrl = "https://picsum.photos/500/500?random=7", AvsBIndex = AvsBIndex),
                AvsBItem(index = 8, imageUrl = "https://picsum.photos/500/500?random=8", AvsBIndex = AvsBIndex),
                AvsBItem(index = 9, imageUrl = "https://picsum.photos/500/500?random=9", AvsBIndex = AvsBIndex),
                AvsBItem(index = 10, imageUrl = "https://picsum.photos/500/500?random=10", AvsBIndex = AvsBIndex),
                AvsBItem(index = 11, imageUrl = "https://picsum.photos/500/500?random=11", AvsBIndex = AvsBIndex),
                AvsBItem(index = 12, imageUrl = "https://picsum.photos/500/500?random=12", AvsBIndex = AvsBIndex),
                AvsBItem(index = 13, imageUrl = "https://picsum.photos/500/500?random=13", AvsBIndex = AvsBIndex),
                AvsBItem(index = 14, imageUrl = "https://picsum.photos/500/500?random=14", AvsBIndex = AvsBIndex),
                AvsBItem(index = 15, imageUrl = "https://picsum.photos/500/500?random=15", AvsBIndex = AvsBIndex),
                AvsBItem(index = 16, imageUrl = "https://picsum.photos/500/500?random=16", AvsBIndex = AvsBIndex),
                AvsBItem(index = 17, imageUrl = "https://picsum.photos/500/500?random=17", AvsBIndex = AvsBIndex)
            )
        )
    }

    override suspend fun getRandomAvsB(): AvsB {
        val randomNumber = Random.nextInt(5)
        return getAvsB(randomNumber)
    }

    override suspend fun getBookmarkAvsBList(pageIdx: Int, pageSize: Int): List<AvsB> {
        val temp = mutableListOf<AvsB>()
        for (i: Int in 1..pageSize) {
            temp.add(
                AvsB(
                    index = pageIdx * pageSize,
                    title = "${pageIdx * pageSize + i}번째 북마크",
                    updateDate = "",
                    thumbnailUrl = "https://picsum.photos/seed/picsum/500/500",
                    isLike = false,
                    primaryColor = "#000000"
                )
            )
        }
        return temp
    }
}