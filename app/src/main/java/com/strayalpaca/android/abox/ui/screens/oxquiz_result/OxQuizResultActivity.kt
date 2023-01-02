package com.strayalpaca.android.abox.ui.screens.oxquiz_result

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.strayalpaca.android.abox.model.const.INTENT_KEY_SOLVED_OX_QUIZ
import com.strayalpaca.android.abox.ui.components.BackButton
import com.strayalpaca.android.abox.ui.components.ListResultDot
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import com.strayalpaca.android.domain.model.OXQuizItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.strayalpaca.android.abox.R
import com.strayalpaca.android.abox.ui.components.OxQuizCard
import com.strayalpaca.android.abox.ui.components.PostCard
import com.strayalpaca.android.abox.ui.components.viewPager.Pager

@AndroidEntryPoint
class OxQuizResultActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: OxQuizResultViewModel.OxQuizResultAssistedFactory

    @Suppress("DEPRECATION", "UNCHECKED_CAST")
    private val viewModel: OxQuizResultViewModel by viewModels {
        val solvedOxQuizList =
            intent.getSerializableExtra(INTENT_KEY_SOLVED_OX_QUIZ) as List<OXQuizItem>
        OxQuizResultViewModel.provideFactory(viewModelFactory, solvedOxQuizList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ABOXTheme {
                Box {
                    BackButton(modifier = Modifier.align(Alignment.TopStart))

                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

                        val (resultCardList, topArea, bottomArea) = createRefs()
                        val currentPosition = viewModel.currentPosition.collectAsState()

                        Box(modifier = Modifier.constrainAs(resultCardList) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }) {
                            Pager(items = viewModel.oxQuizList, onItemSelect = { position, _ ->
                                viewModel.setCurrentPosition(position)
                            }) { quiz ->
                                OxQuizCard(oxQuizItem = quiz)
                            }
                        }

                        Row(
                            modifier = Modifier
                                .constrainAs(topArea) {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(resultCardList.top)
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ListResultDot(
                                amountOfItem = viewModel.oxQuizList.size,
                                currentPosition = currentPosition.value,
                                answerResultList = viewModel.getAnswerResultList()
                            )
                            Text(
                                text = "${viewModel.currentRatio}%",
                                style = MaterialTheme.typography.h1,
                                color = MaterialTheme.colors.onBackground,
                                modifier = Modifier.padding(start = 24.dp)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .constrainAs(bottomArea) {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    top.linkTo(resultCardList.bottom)
                                    bottom.linkTo(parent.bottom)
                                }
                        ) {
                            Text(
                                text = stringResource(id = R.string.toggle_show_description_of_quiz),
                                modifier = Modifier
                                    .padding(24.dp)
                                    .weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = stringResource(id = R.string.go_back),
                                modifier = Modifier
                                    .padding(24.dp)
                                    .weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview("OxQuizResultActivityPreview")
fun OxQuizResultActivityPreview() {
    ABOXTheme {
        Box(Modifier.fillMaxSize()) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (resultCardList, topArea, bottomArea) = createRefs()
                val currentPosition = 5

                Box(modifier = Modifier.constrainAs(resultCardList) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }) {
                    Pager(items = listOf(1, 2, 3, 4, 5, 6, 7)) { quiz ->
                        PostCard()
                    }
                }

                Row(
                    modifier = Modifier
                        .constrainAs(topArea) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(resultCardList.top)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ListResultDot(
                        amountOfItem = 16,
                        currentPosition = currentPosition,
                        answerResultList = listOf(
                            true,
                            false,
                            false,
                            true,
                            true,
                            true,
                            true,
                            true,
                            true,
                            false,
                            true,
                            true,
                            false,
                            false,
                            true,
                            false
                        )
                    )
                    Text(
                        text = "63%",
                        style = MaterialTheme.typography.h1,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(start = 24.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(bottomArea) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(resultCardList.bottom)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    Text(
                        text = stringResource(id = R.string.toggle_show_description_of_quiz),
                        modifier = Modifier
                            .padding(24.dp)
                            .weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(id = R.string.go_back),
                        modifier = Modifier
                            .padding(24.dp)
                            .weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}