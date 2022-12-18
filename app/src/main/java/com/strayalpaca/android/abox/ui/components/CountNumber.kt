package com.strayalpaca.android.abox.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.IntOffset
import com.strayalpaca.android.abox.model.const.STACK_ANIMATION_DURATION
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun CountNumber(modifier: Modifier = Modifier, totalCount: Int, currentCount: Int) {
    val currentCountNumberAlpha = remember { Animatable(initialValue = 0f) }
    val totalCountNumberAlpha = remember { Animatable(initialValue = 1f) }
    val offsetY = remember { Animatable(initialValue = 0f) }
    val showingCurrentCount = remember {
        mutableStateOf(0)
    }

    LaunchedEffect(currentCount) {
        if (currentCount <= totalCount) {
            launch {
                currentCountNumberAlpha.animateTo(targetValue = 0f, animationSpec = tween(STACK_ANIMATION_DURATION))
                showingCurrentCount.value = currentCount
                currentCountNumberAlpha.animateTo(targetValue = 1f, animationSpec = tween(STACK_ANIMATION_DURATION))
            }
            launch {
                offsetY.animateTo(targetValue = -16f, animationSpec = tween(STACK_ANIMATION_DURATION))
                offsetY.snapTo(16f)
                offsetY.animateTo(targetValue = 0f, animationSpec = tween(STACK_ANIMATION_DURATION))
            }
        }
        else {
            launch {
                currentCountNumberAlpha.animateTo(targetValue = 0f, animationSpec = tween(STACK_ANIMATION_DURATION))
            }
            launch {
                totalCountNumberAlpha.animateTo(targetValue = 0f, animationSpec = tween(STACK_ANIMATION_DURATION))
            }
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${showingCurrentCount.value}",
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .alpha(currentCountNumberAlpha.value)
                .offset { IntOffset(x = 0, y = offsetY.value.roundToInt()) }
        )
        Text(
            text = " / $totalCount",
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.alpha(totalCountNumberAlpha.value)
        )
    }
}