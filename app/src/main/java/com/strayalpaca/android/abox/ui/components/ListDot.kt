package com.strayalpaca.android.abox.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ListDot(modifier: Modifier = Modifier, amountOfItem: Int, currentPosition: Int) {
    val alpha = remember { Animatable(initialValue = 0f) }
    val offsetY = remember { Animatable(initialValue = 0f) }
    val amountOfDot = remember { mutableStateOf(value = amountOfItem) }

    // ui 에 표시되는 dot 의 개수는 alpha 를 0으로 변경한 이후 변경됩니다.
    // dot 16->8로 개수 변경시, 16개 알파값 0 -> 8개로 변경 -> 알파값 1로 변경 과정을 거칩니다.
    LaunchedEffect(amountOfItem) {
        launch {
            alpha.animateTo(targetValue = 0f, animationSpec = tween(500))
            amountOfDot.value = amountOfItem
            alpha.animateTo(targetValue = 1f, animationSpec = tween(500))
        }
        launch {
            offsetY.animateTo(targetValue = -16f, animationSpec = tween(500))
            offsetY.snapTo(16f)
            offsetY.animateTo(targetValue = 0f, animationSpec = tween(500))
        }
    }

    Column(
        modifier = modifier.alpha(alpha.value).offset { IntOffset(x = 0, y = offsetY.value.roundToInt()) }.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 0 until amountOfDot.value step (8)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (j in 0 until 8) {
                    val index = i + j
                    if (index < amountOfDot.value) {
                        key("${amountOfDot.value}_${index}") {
                            NormalDot(
                                isCurrentPosition = index == currentPosition,
                                isPrevPosition = index == (currentPosition - 1)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NormalDot(isCurrentPosition: Boolean = false, isPrevPosition: Boolean = false) {
    val selectedColor = MaterialTheme.colors.onSurface
    val unSelectedColor = Color.Gray
    val dotColor = remember {
        androidx.compose.animation.Animatable(if (isPrevPosition) selectedColor else unSelectedColor)
    }

    LaunchedEffect(isCurrentPosition, isPrevPosition) {
        if (isCurrentPosition) {
            dotColor.animateTo(targetValue = selectedColor, animationSpec = tween(1000))
        } else {
            dotColor.animateTo(targetValue = unSelectedColor, animationSpec = tween(1000))
        }
    }

    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(dotColor.value)
    )
}