package com.strayalpaca.android.domain.model

class AvsB(
    index : Int,
    title : String,
    primaryColor : String,
    updateDate : String,
    thumbnailUrl : String,
    isLike : Boolean = false,
    val completeCount : Int = 0,
    val itemList : List<AvsBItem> = listOf()
) : Post(index = index, title = title, primaryColor = primaryColor, updateDate = updateDate, thumbnailUrl = thumbnailUrl, isLike = isLike) {

}
