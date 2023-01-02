package com.strayalpaca.android.abox.ui.components.viewPager

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.ceil
import kotlin.math.roundToInt

@Composable
fun <T> Pager(
    modifier: Modifier = Modifier,
    items: List<T>,
    orientation: Orientation = Orientation.Horizontal, // 내 경우 굳이 필요없을 듯? (horizontal 만 사용)
    initialIndex: Int = 0,
    itemFraction: Float = 1f, // container 의 크기 대비 아이템이 차지하는 크기 비율 (horizontal 이면 width)
    itemSpacing: Dp = 0.dp,
    overshootFraction: Float = .5f, // 첫번째, 마지막 아이템의 위치가 container 의 어느 부분에 위치할 것인가 (0.5면 중간)
    onItemSelect: (Int, T) -> Unit = { _, _ -> }, // 아이템이 선택되었을 때 호출 (int 형 파라미터 추가해야 할듯?)
    contentFactory: @Composable (T) -> Unit,
) {
    require(initialIndex in 0..items.lastIndex) { "Initial index out of bounds" }
    require(itemFraction > 0f && itemFraction <= 1f) { "Item fraction must be in the (0f, 1f] range" }
    require(overshootFraction > 0f && itemFraction <= 1f) { "Overshoot fraction must be in the (0f, 1f] range" }

    val scope = rememberCoroutineScope()
    val state = rememberPagerState()

    state.currentIndex = initialIndex
    state.numberOfItems = items.size
    state.itemFraction = itemFraction
    state.overshootFraction = overshootFraction
    state.itemSpacing = with(LocalDensity.current) { itemSpacing.toPx() }
    state.orientation = orientation
    state.listener = { index -> onItemSelect(index, items[index]) }
    state.scope = scope

    Layout(
        content = {
            items.map { item ->
                Box(
                    modifier = when (orientation) {
                        Orientation.Horizontal -> Modifier.fillMaxWidth()
                        Orientation.Vertical -> Modifier.fillMaxHeight()
                    },
                    contentAlignment = Alignment.Center,
                ) {
                    contentFactory(item)
                }
            }
        },
        modifier = modifier
            .clipToBounds()
            .then(state.inputModifier) // 드래그 핸들링
    ) { measurableList, constraints ->
        val dimension = constraints.dimension(orientation) // 방향에 따라 maxWidth, maxHeight
        val looseConstraints = constraints.toLooseConstraints(
            orientation,
            state.itemFraction
        ) // 수직 방향에 대한 dimension 이 0인 constraints
        val placeableList =
            measurableList.map { measurable -> measurable.measure(looseConstraints) } //
        val size = placeableList.getSize(orientation, dimension)

        val itemDimension = (dimension * state.itemFraction).roundToInt()
        state.itemDimension = itemDimension
        val halfItemDimension = itemDimension / 2

        layout(size.width, size.height) {
            val centerOffset = dimension / 2 - halfItemDimension
            val dragOffset = state.dragOffset.value
            val roundedDragOffset = dragOffset.roundToInt()
            val spacing = state.itemSpacing.roundToInt()
            val itemDimensionWithSpace =
                itemDimension + state.itemSpacing // itemDimension + spacing 과의 차이점?

            // 현재 보여지는 첫 번째 view 의 인덱스와 마지막 view 의 인덱스
            val first = ceil(
                (dragOffset - itemDimension - centerOffset) / itemDimensionWithSpace
            ).toInt().coerceAtLeast(0)
            val last = ((dimension + dragOffset - centerOffset) / itemDimensionWithSpace).toInt()
                .coerceAtMost(items.lastIndex)

            // 각 아이템마다 드래그 위치에 기반하여 위치 계산
            for (i in first..last) {
                val offset = i * (itemDimension + spacing) - roundedDragOffset + centerOffset
                placeableList[i].place(
                    x = when (orientation) {
                        Orientation.Horizontal -> offset
                        Orientation.Vertical -> 0
                    },
                    y = when (orientation) {
                        Orientation.Horizontal -> 0
                        Orientation.Vertical -> offset
                    }
                )

            }
        }
    }

    LaunchedEffect(key1 = items, key2 = initialIndex) {
        state.snapTo(initialIndex)
    }
}

@Composable
private fun rememberPagerState(): PagerState = remember {
    PagerState()
}

// 방향에 대한 최대 dimension 을 리턴
private fun Constraints.dimension(orientation: Orientation) = when (orientation) {
    Orientation.Horizontal -> {
        maxWidth
    }
    Orientation.Vertical -> {
        minWidth
    }
}

// 방향에 수직인 요소의 dimension 을 0으로 세팅함 -> 왜?
private fun Constraints.toLooseConstraints(
    orientation: Orientation, itemFraction: Float
): Constraints {
    val dimension = dimension(orientation)
    val adjustedDimension = (dimension * itemFraction).roundToInt()
    return when (orientation) {
        Orientation.Horizontal -> {
            copy(
                minWidth = adjustedDimension,
                maxWidth = adjustedDimension,
                minHeight = 0,
            )
        }
        Orientation.Vertical -> {
            copy(
                minWidth = 0,
                minHeight = adjustedDimension,
                maxHeight = adjustedDimension,
            )
        }
    }
}

// 방향에 수직인 요소의 최대 크기를 얻기 위해 사용
// 페이저를 구성하는 view 들의 placeable 리스트 중 가장 큰 크기를 구하는 과정
private fun List<Placeable>.getSize(orientation: Orientation, dimension: Int): IntSize =
    when (orientation) {
        Orientation.Horizontal -> {
            IntSize(
                width = dimension,
                height = maxByOrNull { it.height }?.height ?: 0
            )
        }
        Orientation.Vertical -> {
            IntSize(
                width = maxByOrNull { it.width }?.width ?: 0,
                height = dimension
            )
        }
    }
