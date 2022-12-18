package com.strayalpaca.android.abox.ui.screens.oxquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
class OxQuizActivity : ComponentActivity() {

    @Inject
    lateinit var oxQuizViewModelFactory: OxQuizViewModel.OxQuizAssistedFactory

    private val viewModel: OxQuizViewModel by viewModels {
        OxQuizViewModel.provideFactory(assistedFactory = oxQuizViewModelFactory, 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ABOXTheme {
                Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
                    val loadingDialogShow = viewModel.loadingDialogShow.collectAsState()

                    BackButton(modifier = Modifier.align(Alignment.TopStart))

                    SwipeActionCircles(
                        modifier = Modifier.align(Alignment.Center),
                        swipeActionFlow = viewModel.swipeOrientation
                    )

                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                        val remainOxQuizItems = viewModel.remainOxQuizItemList.collectAsState()
                        val currentPosition = viewModel.currentPosition.collectAsState()

                        val (stack, dotList, countNumber) = createRefs()

                        Box(modifier = Modifier.constrainAs(stack) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }) {
                            SwipeStack(
                                dataList = remainOxQuizItems.value,
                                swipeStackListener = viewModel,
                                modifier = Modifier.align(Alignment.Center)
                            ) { modifier, oxQuizItem ->
                                key(oxQuizItem) {
                                    OxQuizCard(modifier = modifier, oxQuizItem = oxQuizItem)
                                }
                            }
                        }

                        CountNumber(
                            totalCount = viewModel.oxQuizItemList.size,
                            currentCount = (currentPosition.value + 1),
                            modifier = Modifier.constrainAs(countNumber) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(stack.top)
                            }
                        )

                        ListDot(
                            amountOfItem = viewModel.oxQuizItemList.size,
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