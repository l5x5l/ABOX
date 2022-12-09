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
fun ListDot(modifier: Modifier = Modifier, round: Int, currentPosition: Int) {
    val alpha = remember { Animatable(initialValue = 0f) }
    val offsetY = remember { Animatable(initialValue = 0f) }
    val amountOfItem = remember { mutableStateOf(value = round / 2) }

    LaunchedEffect(round) {
        launch {
            alpha.animateTo(targetValue = 0f, animationSpec = tween(500))
            amountOfItem.value = round / 2
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
        for (i in 0 until amountOfItem.value step (8)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (j in 0 until 8) {
                    val index = i + j
                    if (index < amountOfItem.value) {
                        key("${amountOfItem.value}_${index}") {
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