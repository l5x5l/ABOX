package com.strayalpaca.android.abox.ui.components.swipeStack


import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.tween
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.unit.IntOffset
import com.strayalpaca.android.abox.util.getWindowWidth
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

const val BOTTOM_POSITION = 2
const val TOP_POSITION = 0
const val STACK_INTERVAL_Y = 16f
const val STACK_ANIMATION_DURATION = 500

@SuppressLint("ReturnFromAwaitPointerEventScope", "MultipleAwaitPointerEventScopes")
fun <T> Modifier.swipe(
    swipeStackListener: SwipeStackListener<T>,
    itemPosition: Int,
    item: T,
    isLastItem: Boolean = false
): Modifier =
    composed {
        val offsetX = remember { Animatable(0f) }
        val offsetY = remember { Animatable(STACK_INTERVAL_Y * (itemPosition + 1)) }
        val alpha = remember { Animatable(if (itemPosition == BOTTOM_POSITION) 0f else 1f) }
        val screenWidth = getWindowWidth()
        val rotateAnglePerOffsetX = 15 / screenWidth
        val canGesture = remember { mutableStateOf(true) }

        LaunchedEffect(itemPosition) {
            launch {
                offsetY.animateTo(STACK_INTERVAL_Y * itemPosition, tween(STACK_ANIMATION_DURATION))
            }
            if (itemPosition == BOTTOM_POSITION) {
                launch {
                    alpha.animateTo(1f, tween(STACK_ANIMATION_DURATION))
                }
            }
        }

        pointerInput(Unit) {
            val decay = splineBasedDecay<Float>(this)
            coroutineScope {
                while (itemPosition == TOP_POSITION && canGesture.value) {
                    val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                    val velocityTracker = VelocityTracker()

                    offsetX.stop()
                    awaitPointerEventScope {
                        drag(pointerId) { change ->
                            launch {
                                offsetX.snapTo(offsetX.value + change.positionChange().x)
                                offsetY.snapTo(offsetY.value + change.positionChange().y)
                            }
                            velocityTracker.addPosition(
                                change.uptimeMillis,
                                change.position
                            )
                        }
                    }

                    val velocityX = velocityTracker.calculateVelocity().x
                    val velocityY = velocityTracker.calculateVelocity().y

                    val targetOffsetX = decay.calculateTargetValue(offsetX.value, velocityX)

                    if (targetOffsetX.absoluteValue <= size.width / 1.5) {
                        launch {
                            offsetX.animateTo(targetValue = 0f, initialVelocity = velocityX)
                        }
                        launch {
                            offsetY.animateTo(
                                targetValue = STACK_INTERVAL_Y * itemPosition,
                                initialVelocity = velocityY
                            )
                        }
                    } else {
                        canGesture.value = false
                        launch {
                            if (targetOffsetX < 0) {
                                offsetX.animateTo(targetValue = -(screenWidth * 1.5f))
                                swipeStackListener.onSwipeToLeft(item)
                            } else {
                                offsetX.animateTo(targetValue = (screenWidth * 1.5f))
                                swipeStackListener.onSwipeToRight(item)
                            }

                            if (isLastItem) {
                                swipeStackListener.onStackEmpty()
                            }
                        }
                    }
                }
            }
        }
            .offset {
                IntOffset(
                    x = offsetX.value.roundToInt(),
                    y = offsetY.value.roundToInt()
                )
            }
            .rotate(rotateAnglePerOffsetX * offsetX.value)
            .alpha(alpha.value)
    }