package com.strayalpaca.android.domain.model

import java.io.Serializable

data class OXQuizItem(
    val index : Int,
    val imageUrl : String?,
    val quizText : String,
    val correctAnswer : Boolean,
    var totalAmountOfAnswer : Int,
    var totalAmountOfCorrectAnswer : Int,
    val categoryIndex : Int,
    val answerDescription : String ?= null,
    var userAnswer : Boolean ?= null
) : Serializable {
    fun applyAnswer(answer : Boolean) {
        userAnswer = answer
        totalAmountOfAnswer++
        if (userAnswer == correctAnswer) {
            totalAmountOfCorrectAnswer++
        }
    }
}
