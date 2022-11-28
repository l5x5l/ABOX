package com.strayalpaca.android.abox.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalpaca.android.abox.R


@Composable
fun SplashLogo(animationDuration: Int = 500) {
    val animationOffset = remember { Animatable(1f) }

    LaunchedEffect(animationOffset) {
        animationOffset.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = animationDuration)
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colors.onBackground)
        ) {
            Row(
                Modifier
                    .height(140.dp)
                    .padding(start = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_a),
                    contentDescription = "A in Logo",
                    modifier = Modifier
                        .width(108.dp)
                        .offset(y = -(100 * animationOffset.value).dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                )
                Image(
                    painter = painterResource(id = R.drawable.image_b),
                    contentDescription = "B in Logo",
                    modifier = Modifier
                        .width(108.dp)
                        .align(Alignment.Bottom)
                        .offset(y = (100 * animationOffset.value).dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                )
            }
        }

        Surface(
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colors.onBackground)
        ) {
            Row(
                Modifier
                    .height(140.dp)
                    .padding(start = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_o),
                    contentDescription = "O in Logo",
                    modifier = Modifier
                        .width(108.dp)
                        .align(Alignment.Bottom)
                        .offset(y = (100 * animationOffset.value).dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                )

                Image(
                    painter = painterResource(id = R.drawable.image_x),
                    contentDescription = "X in Logo",
                    modifier = Modifier
                        .width(108.dp)
                        .offset(y = -(100 * animationOffset.value).dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    SplashLogo()
}