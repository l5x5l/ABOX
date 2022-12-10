package com.strayalpaca.android.abox.ui.components.swipeStack

import com.strayalpaca.android.domain.model.SwipeOrientation

interface SwipeStackListener<T> {
    fun onSwipeToLeft(item : T)
    fun onSwipeToRight(item : T)
    fun onStackEmpty()
    fun onSwipeAnimationStart(swipeOrientation: SwipeOrientation)
}