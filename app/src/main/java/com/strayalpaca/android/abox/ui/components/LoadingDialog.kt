package com.strayalpaca.android.abox.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.strayalpaca.android.abox.ui.theme.ABOXTheme

@Composable
fun LoadingDialog() {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 600
            }
        )
    )

    Dialog(
        onDismissRequest = {

        }
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                Modifier.padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = 1f,
                    modifier = Modifier
                        .size(80.dp)
                        .rotate(angle)
                        .border(
                            width = 12.dp,
                            brush = Brush.sweepGradient(
                                listOf(
                                    MaterialTheme.colors.background,
                                    MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
                                    MaterialTheme.colors.onBackground
                                )
                            ),
                            shape = CircleShape
                        )
                        .align(Alignment.Center),
                    strokeWidth = 1.dp,
                    color = MaterialTheme.colors.background
                )
            }
        }

    }
}

@Composable
@Preview("LoadingDialogPreview")
fun LoadingDialogPreview() {
    ABOXTheme {
        Box(Modifier.fillMaxSize()) {
            LoadingDialog()
        }
    }
}