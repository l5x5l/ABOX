package com.strayalpaca.android.domain.model

class OXQuizCategory(
    index : Int,
    title : String,
    primaryColor : String,
    updateDate : String,
    thumbnailUrl : String,
    isLike : Boolean = false,
    amountOfQuiz : Int
) : Post(index = index, title = title, primaryColor = primaryColor, updateDate = updateDate, thumbnailUrl = thumbnailUrl, isLike = isLike, postType = PostType.OX) {

}