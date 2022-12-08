package com.strayalpaca.android.domain.model

// round 는 16강, 8강, 4강, 결승을 의미하며 각각 16-8-4-2 로 표시됩니다.
class AvsBContent(val A : AvsBItem, val B : AvsBItem, val round : Int, var vote : AB ?= null) {
    fun getVotedItem() : AvsBItem {
        return when (vote) {
            AB.A -> { A }
            AB.B -> { B }
            else -> {
                throw IllegalStateException("This AvsBContent is not voted yet")
            }
        }

    }
}