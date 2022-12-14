package com.strayalpaca.android.abox.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import com.strayalpaca.android.domain.model.SwipeOrientation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun SwipeActionCircles(modifier: Modifier = Modifier, swipeActionFlow : Flow<SwipeOrientation>? = null) {

    val dotSize = 68
    val aCircleAlpha = remember { Animatable(1f) }
    val bCircleAlpha = remember { Animatable(1f) }
    val aCircleScale = remember { Animatable(1f) }
    val bCircleScale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        swipeActionFlow?.collect{ orientation ->
            when (orientation) {
                SwipeOrientation.LEFT -> {
                    launch {
                        aCircleAlpha.animateTo(targetValue = 0f, animationSpec = tween(500))
                        aCircleAlpha.animateTo(targetValue = 1f, animationSpec = tween(500))
                    }
                    launch {
                        aCircleScale.animateTo(targetValue = 10f, animationSpec = tween(500))
                        aCircleScale.snapTo(0f)
                        aCircleScale.animateTo(targetValue = 1f, animationSpec = tween(500))
                    }
                }

                SwipeOrientation.RIGHT -> {
                    launch {
                        bCircleAlpha.animateTo(targetValue = 0f, animationSpec = tween(500))
                        bCircleAlpha.animateTo(targetValue = 1f, animationSpec = tween(500))
                    }
                    launch {
                        bCircleScale.animateTo(targetValue = 10f, animationSpec = tween(500))
                        bCircleScale.snapTo(0f)
                        bCircleScale.animateTo(targetValue = 1f, animationSpec = tween(500))
                    }
                }
            }
        }
    }

    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Canvas(
            modifier = Modifier
                .size(size = dotSize.dp)
                .offset(x = -(dotSize / 2).dp)
                .zIndex(-1f)
                .scale(aCircleScale.value)
                .alpha(aCircleAlpha.value)
        ) {
            drawCircle(
                color = Color.Gray,
                radius = 34.dp.toPx()
            )
        }

        Canvas(
            modifier = Modifier
                .size(size = dotSize.dp)
                .offset(x = (dotSize / 2).dp)
                .zIndex(-1f)
                .scale(bCircleScale.value)
                .alpha(bCircleAlpha.value)
        ) {
            drawCircle(
                color = Color.Gray,
                radius = 34.dp.toPx()
            )
        }
    }
}

@Composable
@Preview("SwipeActionDotPreview")
fun SwipeActionDotPreview() {
    ABOXTheme() {
        Box(modifier = Modifier.fillMaxSize()){
            SwipeActionCircles(Modifier.align(Alignment.Center))
        }
    }
}