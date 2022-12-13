package com.strayalpaca.android.domain.model

class OXQuizItem(
    val index : Int,
    val imageUrl : String?,
    val quizText : String,
    val correctAnswer : Boolean,
    var totalAmountOfAnswer : Int,
    var totalAmountOfCorrectAnswer : Int,
    val categoryIndex : Int,
    val answerDescription : String ?= null,
    var userAnswer : Boolean ?= null
) {
    fun submitAnswer(answer : Boolean) {
        userAnswer = answer
        totalAmountOfAnswer++
        if (userAnswer == correctAnswer) {
            totalAmountOfCorrectAnswer++
        }
    }
}
