package com.strayalpaca.android.abox.ui.screens.avsb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.strayalpaca.android.abox.ui.components.*
import com.strayalpaca.android.abox.ui.components.swipeStack.SwipeStack
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import dagger.hilt.android.AndroidEntryPoint
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
            ABOXTheme {
                Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
                    val loadingDialogShow = viewModel.loadingDialogShow.collectAsState()

                    BackButton(modifier = Modifier.align(Alignment.TopStart))

                    SwipeActionCircles(modifier = Modifier.align(Alignment.Center), swipeActionFlow = viewModel.swipeOrientation)

                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                        val currentPosition = viewModel.currentPosition.collectAsState()
                        val currentRound = viewModel.currentABRound.collectAsState()
                        val abContent = viewModel.abContentList.collectAsState()

                        val (stack, roundNumber, dotList) = createRefs()

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
                            amountOfItem = currentRound.value / 2,
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
