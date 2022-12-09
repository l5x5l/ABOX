package com.strayalpaca.android.abox.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ListDot(modifier: Modifier = Modifier, amountOfItem: Int, currentPosition: Int) {
    val alpha = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val positionList = remember { mutableStateListOf<Int>() }

    LaunchedEffect(amountOfItem) {
        positionList.clear()
        positionList.addAll((0 until amountOfItem).toList())
        launch {
            alpha.animateTo(targetValue = 0f, animationSpec = tween(500))
            alpha.animateTo(targetValue = 1f, animationSpec = tween(500))
        }
        launch {
            offsetY.animateTo(targetValue = -20f, animationSpec = tween(500))
            offsetY.snapTo(20f)
            offsetY.animateTo(targetValue = 0f, animationSpec = tween(500))
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 0 until amountOfItem step (8)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (j in 0 until 8) {
                    val index = i + j
                    if (index < amountOfItem) {
                        key("${amountOfItem}_${index}") {
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