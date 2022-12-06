package com.strayalpaca.android.abox.ui.components.swipeStack

interface SwipeStackListener<T> {
    fun onSwipeToLeft(item : T)
    fun onSwipeToRight(item : T)
    fun onStackEmpty()
}