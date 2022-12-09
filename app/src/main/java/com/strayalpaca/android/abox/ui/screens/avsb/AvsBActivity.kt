package com.strayalpaca.android.abox.ui.screens.avsb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.strayalpaca.android.abox.ui.components.ListDot
import com.strayalpaca.android.abox.ui.components.RoundNumber
import com.strayalpaca.android.abox.ui.components.swipeStack.SwipeStack
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AvsBActivity : ComponentActivity() {
    private val viewModel: AvsBViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getAvsB()

        setContent {
            val currentPosition = viewModel.currentPosition.collectAsState()
            val currentRound = viewModel.currentABRound.collectAsState()
            val abContent = viewModel.abContentList.collectAsState()

            val aCircleAlpha = remember { Animatable(1f) }
            val bCircleAlpha = remember { Animatable(1f) }
            val aCircleScale = remember { Animatable(1f) }
            val bCircleScale = remember { Animatable(1f) }

            ABOXTheme {
                Box(){

                    // back button

                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
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
                            )
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

                    // middle circle 2개
                }
            }
        }
    }
}
