package com.strayalpaca.android.domain.model

enum class PostType {
    AB, OX;

    companion object {
        fun getRandomType() : PostType {
            return values().random()
        }
    }
}