package com.strayalpaca.android.abox.oxQuizTest

import com.strayalpaca.android.abox.MainCoroutineRule
import com.strayalpaca.android.abox.ui.screens.oxquiz.OxQuizViewModel
import com.strayalpaca.android.data.repositoryImpl.OxQuizTestRepository
import com.strayalpaca.android.domain.usecase.UseCaseGetOxQuiz
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OxQuizTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `After Load OxQuiz, Amount of OxQuizItem is in from 10 to 15`() {
        runTest {
            val viewModel = OxQuizViewModel(UseCaseGetOxQuiz(OxQuizTestRepository()))
            viewModel.getOxQuiz(1)
            advanceUntilIdle()

            val temp = viewModel.oxQuizItemList.size
            assertTrue { temp in 10..15 }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `After Solve Quiz List, this result applied to OxQuizItem's answerList`() {
        runTest {
            val viewModel = OxQuizViewModel(UseCaseGetOxQuiz(OxQuizTestRepository()))
            viewModel.getOxQuiz(1)
            advanceUntilIdle()

            while (viewModel.remainOxQuizItemList.value.isNotEmpty()) {
                viewModel.onSwipeToLeft(viewModel.remainOxQuizItemList.value[0])
            }
            viewModel.onStackEmpty()

            var answerCount = 0
            for (item in viewModel.oxQuizItemList) {
                answerCount += item.totalAmountOfAnswer
            }

            assertEquals(answerCount, viewModel.oxQuizItemList.size)
        }
    }

}