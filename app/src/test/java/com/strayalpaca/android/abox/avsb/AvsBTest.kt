package com.strayalpaca.android.abox.avsb

import com.strayalpaca.android.abox.MainCoroutineRule
import com.strayalpaca.android.abox.ui.screens.avsb.AvsBViewModel
import com.strayalpaca.android.data.repositoryImpl.AvsBTestRepository
import com.strayalpaca.android.domain.usecase.UseCaseGetAvsB
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class AvsBTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `After Load AvsB, Amount of AvsB's First round Item is powers of 2`() {
        runTest {
            val viewModel = AvsBViewModel(UseCaseGetAvsB(AvsBTestRepository()))
            viewModel.getAvsB()
            advanceUntilIdle()

            val expected = 8
            assertEquals(expected, viewModel.ab?.abContentList?.size)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `After Finish single round, Next Round will be created`() {
        runTest {
            val viewModel = AvsBViewModel(UseCaseGetAvsB(AvsBTestRepository()))
            viewModel.getAvsB()
            advanceUntilIdle()

            while(viewModel.ab?.abContentList?.size!! > 0) {
                viewModel.onSwipeToLeft(viewModel.ab!!.abContentList[0])
            }
            viewModel.onStackEmpty()

            while(viewModel.ab?.abContentList?.size!! > 0) {
                viewModel.onSwipeToLeft(viewModel.ab!!.abContentList[0])
            }
            viewModel.onStackEmpty()

            val expected = 2
            assertEquals(expected, viewModel.ab?.abContentList?.size)
        }
    }

}