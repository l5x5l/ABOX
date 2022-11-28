package com.strayalpaca.android.abox.ui.components

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.strayalpaca.android.abox.R
import com.strayalpaca.android.abox.ui.theme.ABOXTheme

@Composable
fun HomeCategory(categoryTitle: String, categoryDescription: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, MaterialTheme.colors.onBackground),
    ) {
        Column(
            modifier = Modifier
                .aspectRatio(1f)
                .clickable {

                }
                .background(MaterialTheme.colors.background)
        ) {
            Text(
                text = categoryTitle,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 14.dp, top = 18.dp)
            )
            Text(
                text = categoryDescription,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 14.dp)
            )
        }
    }
}

@Composable
fun HomeCategoryList() {
    val circleSize = LocalConfiguration.current.screenWidthDp * 1.2
    val shadowColor = MaterialTheme.colors.onBackground.copy(alpha = 0.05f)
    val animationValue = remember { Animatable(0f) }

    LaunchedEffect(animationValue) {
        animationValue.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.Center)
                .offset(y = (10 - animationValue.value * 20).dp)
                .alpha(alpha = animationValue.value)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    HomeCategory(stringResource(id = R.string.AvsB_category), stringResource(id = R.string.description_AvsB_category))
                }
                Box(modifier = Modifier.weight(1f)) {
                    HomeCategory(stringResource(id = R.string.OXQuiz_category), stringResource(id = R.string.description_OXQuiz_category))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    HomeCategory(stringResource(id = R.string.Random_category), stringResource(id = R.string.description_random_category))
                }
                Box(modifier = Modifier.weight(1f)) {
                    HomeCategory(stringResource(id = R.string.bookmark_category), stringResource(id = R.string.description_bookmark_category))
                }
            }
        }

        Canvas(
            modifier = Modifier
                .size(size = circleSize.dp)
                .align(Alignment.Center)
                .offset(x = 24.dp)
                .zIndex(-1f)
                .scale(animationValue.value)
        ) {
            drawCircle(
                color = shadowColor,
                radius = (circleSize / 2).dp.toPx()
            )
        }
    }
}



@Preview(showBackground = true)
@Preview(name = "dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeCategoryNoAnimation() {
    val circleSize = LocalConfiguration.current.screenWidthDp * 1.2
    val shadowColor = MaterialTheme.colors.onBackground.copy(alpha = 0.05f)

    ABOXTheme {

        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.Center)
                    .offset(y = (-10).dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        HomeCategory(stringResource(id = R.string.AvsB_category), stringResource(id = R.string.description_AvsB_category))
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        HomeCategory(stringResource(id = R.string.OXQuiz_category), stringResource(id = R.string.description_OXQuiz_category))
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        HomeCategory(stringResource(id = R.string.Random_category), stringResource(id = R.string.description_random_category))
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        HomeCategory(stringResource(id = R.string.bookmark_category), stringResource(id = R.string.description_bookmark_category))
                    }
                }
            }

            Canvas(
                modifier = Modifier
                    .size(size = circleSize.dp)
                    .align(Alignment.Center)
                    .offset(x = 24.dp)
                    .zIndex(-1f)
            ) {
                drawCircle(
                    color = shadowColor,
                    radius = (circleSize / 2).dp.toPx()
                )
            }
        }
    }
}

