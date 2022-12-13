package com.strayalpaca.android.abox.ui.screens.avsb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.strayalpaca.android.abox.ui.components.*
import com.strayalpaca.android.abox.ui.components.swipeStack.SwipeStack
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import com.strayalpaca.android.domain.model.SwipeOrientation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AvsBActivity : ComponentActivity() {

    @Inject
    lateinit var abViewModelFactory : AvsBViewModel.AbAssistedFactory

    private val viewModel: AvsBViewModel by viewModels {
        AvsBViewModel.provideFactory(abViewModelFactory, 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val aCircleAlpha = remember { Animatable(1f) }
            val bCircleAlpha = remember { Animatable(1f) }
            val aCircleScale = remember { Animatable(1f) }
            val bCircleScale = remember { Animatable(1f) }

            LaunchedEffect(Unit) {
                viewModel.swipeOrientation.collect{ orientation ->
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

            ABOXTheme {
                Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
                    val loadingDialogShow = viewModel.loadingDialogShow.collectAsState()

                    BackButton(modifier = Modifier.align(Alignment.TopStart))

                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                        val currentPosition = viewModel.currentPosition.collectAsState()
                        val currentRound = viewModel.currentABRound.collectAsState()
                        val abContent = viewModel.abContentList.collectAsState()

                        val (stack, roundNumber, dotList, aCircle, bCircle) = createRefs()

                        Canvas(
                            modifier = Modifier
                                .size(size = 68.dp)
                                .zIndex(-1f)
                                .scale(aCircleScale.value)
                                .alpha(aCircleAlpha.value)
                                .constrainAs(aCircle) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.start)
                                }
                        ) {
                            drawCircle(
                                color = Color.Gray,
                                radius = 34.dp.toPx()
                            )
                        }

                        Canvas(
                            modifier = Modifier
                                .size(size = 68.dp)
                                .zIndex(-1f)
                                .scale(bCircleScale.value)
                                .alpha(bCircleAlpha.value)
                                .constrainAs(bCircle) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.end)
                                    end.linkTo(parent.end)
                                }
                        ) {
                            drawCircle(
                                color = Color.Gray,
                                radius = 34.dp.toPx()
                            )
                        }

                        Box(modifier = Modifier.constrainAs(stack) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }) {
                            SwipeStack(
                                dataList = abContent.value,
                                swipeStackListener = viewModel,
                                modifier = Modifier.align(Alignment.Center)
                            ) { modifier, avsBContent ->
                                key(avsBContent) {
                                    AvsBCard(
                                        modifier = modifier,
                                        AvsB = avsBContent
                                    )
                                }
                            }
                        }

                        RoundNumber(
                            currentRound = currentRound.value,
                            modifier = Modifier.constrainAs(roundNumber) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(stack.top)
                            }
                        )

                        ListDot(
                            round = currentRound.value,
                            currentPosition = currentPosition.value,
                            modifier = Modifier.constrainAs(dotList) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(stack.bottom)
                                bottom.linkTo(parent.bottom)
                            }
                        )
                    }

                    if (loadingDialogShow.value) {
                        LoadingDialog()
                    }

                }
            }
        }
    }
}
