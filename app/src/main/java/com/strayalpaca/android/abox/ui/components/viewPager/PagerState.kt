package com.strayalpaca.android.abox.ui.components.viewPager

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sign

// https://fvilarino.medium.com/creating-a-viewpager-in-jetpack-compose-332d6a9181a5
class PagerState {
    var currentIndex by mutableStateOf(0)
    var numberOfItems by mutableStateOf(0)
    var itemFraction by mutableStateOf(0f)
    var overshootFraction by mutableStateOf(0f)
    var itemSpacing by mutableStateOf(0f)
    var itemDimension by mutableStateOf(0)
    var orientation by mutableStateOf(Orientation.Horizontal)
    var scope: CoroutineScope? by mutableStateOf(null)
    var listener: (Int) -> Unit by mutableStateOf({})
    val dragOffset = Animatable(0f)

    private val animationSpec = SpringSpec<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )

    suspend fun snapTo(index: Int) {
        dragOffset.snapTo(index.toFloat() * (itemDimension + itemSpacing))
    }
    // 2 - Layout 의 modifier 로 사용됨
    val inputModifier = Modifier.pointerInput(numberOfItems) {
        // 3
        fun itemIndex(offset: Int): Int =
            (offset / (itemDimension + itemSpacing)).roundToInt().coerceIn(0, numberOfItems - 1)

        // 4
        fun updateIndex(offset: Float) {
            val index = itemIndex(offset.roundToInt())
            if (index != currentIndex) {
                currentIndex = index
                listener(index)
            }
        }

        // 5, 첫번째 및 마지막 아이템의 시작/끝 위치를 한정시켜주는 역할
        fun calculateOffsetLimit(): OffsetLimit {
            val dimension = when (orientation) {
                Orientation.Horizontal -> size.width
                Orientation.Vertical -> size.height
            }
            val itemSideMargin = (dimension - itemDimension) / 2f
            return OffsetLimit(
                min = -dimension * overshootFraction + itemSideMargin,
                max = numberOfItems * (itemDimension + itemSpacing) - (1f - overshootFraction) * dimension + itemSideMargin,
            )
        }

        // 6
        forEachGesture {
            // 7
            awaitPointerEventScope {
                // 8, 입력이 감지되었다면 velocitryTracker 와 decay 값을 계산한 후 mouse down 이벤트를 기다림
                val tracker = VelocityTracker()
                val decay = splineBasedDecay<Float>(this)
                val down = awaitFirstDown()
                val offsetLimit = calculateOffsetLimit()

                // 9, 가로/세로부분을 모두 다루어야 하기 때문에 이 부분에 위치한다고 명시
                val dragHandler = { change: PointerInputChange ->
                    scope?.launch {
                        val dragChange = change.calculateDragChange(orientation)
                        dragOffset.snapTo(
                            (dragOffset.value - dragChange).coerceIn(
                                offsetLimit.min,
                                offsetLimit.max
                            )
                        )
                        updateIndex(dragOffset.value)
                    }
                    tracker.addPosition(change.uptimeMillis, change.position)
                }
                // 10
                when (orientation) {
                    Orientation.Horizontal -> horizontalDrag(down.id, dragHandler)
                    Orientation.Vertical -> verticalDrag(down.id, dragHandler)
                }
                // 11 사용자가 손가락을 땐 이후 호출
                val velocity = tracker.calculateVelocity(orientation)
                // 12, 새로운 coroutine 을 생성하는 이유? -> fling animation 함수가 suspend 함수
                // 의문점 -> 이 부분을 실행중 touch down 이벤트가 감지된다면?
                // restricted suspend function?
                scope?.launch {
                    // 13
                    var targetOffset = decay.calculateTargetValue(dragOffset.value, -velocity)
                    val remainder = targetOffset.toInt().absoluteValue % itemDimension
                    val extra = if (remainder > itemDimension / 2f) 1 else 0
                    val lastVisibleIndex =
                        (targetOffset.absoluteValue / itemDimension.toFloat()).toInt() + extra
                    targetOffset = (lastVisibleIndex * itemDimension * targetOffset.sign)
                        .coerceIn(0f, (numberOfItems - 1).toFloat() * itemDimension)
                    // 14
                    dragOffset.animateTo(
                        animationSpec = animationSpec,
                        targetValue = targetOffset,
                        initialVelocity = -velocity
                    ) {
                        updateIndex(value) // 15
                    }
                }
            }
        }
    }

    data class OffsetLimit(
        val min: Float,
        val max: Float,
    )
}

// 스크롤하는 방향에 대한 가속도 계산값 리턴
private fun VelocityTracker.calculateVelocity(orientation: Orientation): Float =
    when (orientation) {
        Orientation.Horizontal -> calculateVelocity().x
        Orientation.Vertical -> calculateVelocity().y
    }

// 스크롤하는 방향에 대한 pointer 인풋 변경값 리턴
private fun PointerInputChange.calculateDragChange(orientation: Orientation): Float =
    when (orientation) {
        Orientation.Horizontal -> {
            positionChange().x
        }
        Orientation.Vertical -> {
            positionChange().y
        }
    }