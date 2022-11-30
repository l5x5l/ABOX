package com.strayalpaca.android.domain.model

data class HomeData(
    val popularPost : List<Post>,
    val recentPost : List<Post>
)