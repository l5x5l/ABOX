package com.strayalpaca.android.data.repositoryImpl

import com.strayalpaca.android.domain.model.HomeData
import com.strayalpaca.android.domain.model.Post
import com.strayalpaca.android.domain.repository.HomeRepository

class HomeTestRepository : HomeRepository {
    override suspend fun getHomeData(): HomeData {
        val popularData = mutableListOf<Post>()
        val recentData = mutableListOf<Post>()

        for (i : Int in 1..10) {
            popularData.add(Post(index = i, title = "인기있는 Post$i", updateDate = "", thumbnailUrl = "", isLike = false, primaryColor = "#000000"))
            recentData.add(Post(index = i, title = "최근 Post$i", updateDate = "", thumbnailUrl = "", isLike = false, primaryColor = "#000000"))
        }

        return HomeData(popularPost = popularData, recentPost = recentData)
    }
}