package com.strayalpaca.android.domain.model

open class Post(
    val index : Int,
    val title : String,
    val updateDate : String,
    val thumbnailUrl : String,
    val isLike : Boolean,
    val primaryColor : String
) {

}