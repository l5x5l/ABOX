package com.strayalpaca.android.abox.ui.components

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.strayalpaca.android.abox.R
import com.strayalpaca.android.abox.ui.screens.avsb_list.AvsBListActivity
import com.strayalpaca.android.abox.ui.screens.oxquiz.OxQuizActivity
import com.strayalpaca.android.abox.ui.screens.oxquiz_category.OxQuizCategoryActivity
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import com.strayalpaca.android.abox.util.findActivity

@Composable
fun HomeCategory(
    categoryTitle: String,
    categoryDescription: String,
    modifier: Modifier = Modifier,
    clickEvent : () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, MaterialTheme.colors.onBackground),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .aspectRatio(1f)
                .clickable {
                    clickEvent()
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
    val activity = LocalContext.current.findActivity()

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
                HomeCategory(
                    stringResource(id = R.string.AvsB_category),
                    stringResource(id = R.string.description_AvsB_category),
                    modifier = Modifier.weight(1f),
                    clickEvent = {
                        val intent = Intent(activity, AvsBListActivity::class.java)
                        activity.startActivity(intent)
                    }
                )

                HomeCategory(
                    stringResource(id = R.string.OXQuiz_category),
                    stringResource(id = R.string.description_OXQuiz_category),
                    modifier = Modifier.weight(1f),
                    clickEvent = {
                        val intent = Intent(activity, OxQuizCategoryActivity::class.java)
                        activity.startActivity(intent)
                    }
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                HomeCategory(
                    stringResource(id = R.string.Random_category),
                    stringResource(id = R.string.description_random_category),
                    modifier = Modifier.weight(1f),
                    clickEvent = {
                        val intent = Intent(activity, OxQuizActivity::class.java)
                        activity.startActivity(intent)
                    }
                )

                HomeCategory(
                    stringResource(id = R.string.bookmark_category),
                    stringResource(id = R.string.description_bookmark_category),
                    modifier = Modifier.weight(1f),
                    clickEvent = {
                        val intent = Intent(activity, AvsBListActivity::class.java)
                        activity.startActivity(intent)
                    }
                )

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

                    HomeCategory(
                        stringResource(id = R.string.AvsB_category),
                        stringResource(id = R.string.description_AvsB_category),
                        modifier = Modifier.weight(1f),
                        clickEvent = {

                        }
                    )

                    HomeCategory(
                        stringResource(id = R.string.OXQuiz_category),
                        stringResource(id = R.string.description_OXQuiz_category),
                        modifier = Modifier.weight(1f),
                        clickEvent = {

                        }
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    HomeCategory(
                        stringResource(id = R.string.Random_category),
                        stringResource(id = R.string.description_random_category),
                        modifier = Modifier.weight(1f),
                        clickEvent = {

                        }
                    )


                    HomeCategory(
                        stringResource(id = R.string.bookmark_category),
                        stringResource(id = R.string.description_bookmark_category),
                        modifier = Modifier.weight(1f),
                        clickEvent = {

                        }
                    )

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

