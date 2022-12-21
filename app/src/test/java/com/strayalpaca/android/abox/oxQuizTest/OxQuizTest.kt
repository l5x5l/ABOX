package com.strayalpaca.android.abox.oxQuizTest

import com.strayalpaca.android.abox.MainCoroutineRule
import com.strayalpaca.android.abox.ui.screens.oxquiz.OxQuizViewModel
import com.strayalpaca.android.data.repositoryImpl.OxQuizTestRepository
import com.strayalpaca.android.domain.usecase.UseCaseGetOxQuiz
import com.strayalpaca.android.domain.usecase.UseCaseSendOxQuizSolveData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.net.SocketTimeoutException
import kotlin.test.assertEquals

class OxQuizTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `After Sending solve data request throw SocketTimeoutException, show ErrorDialog`() {
        runTest {
            val mockRepository : OxQuizTestRepository = mockk<OxQuizTestRepository>(relaxed = true)
            coEvery { mockRepository.sendSolveData(listOf()) } throws SocketTimeoutException("time out")

            val viewModel = OxQuizViewModel(UseCaseGetOxQuiz(mockRepository), UseCaseSendOxQuizSolveData(mockRepository), 1)
            viewModel.getOxQuiz()
            advanceUntilIdle()

            viewModel.sendSolveData()
            advanceUntilIdle()

            assertEquals(viewModel.isShowErrorDialog.value, true)
        }
    }
}