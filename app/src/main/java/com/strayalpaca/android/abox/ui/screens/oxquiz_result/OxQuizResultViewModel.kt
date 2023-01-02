package com.strayalpaca.android.abox.ui.screens.oxquiz_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.strayalpaca.android.domain.model.OXQuizItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OxQuizResultViewModel @AssistedInject constructor(
    @Assisted val oxQuizList : List<OXQuizItem>
) : ViewModel() {

    @AssistedFactory
    interface OxQuizResultAssistedFactory {
        fun create(oxQuizList: List<OXQuizItem>) : OxQuizResultViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(assistedFactory : OxQuizResultAssistedFactory, oxQuizList: List<OXQuizItem>) : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(oxQuizList) as T
            }
        }
    }

    val currentRatio get() = oxQuizList.filter { oxQuizItem -> oxQuizItem.userAnswer == oxQuizItem.correctAnswer }.size / oxQuizList.size

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition = _currentPosition.asStateFlow()

    fun getAnswerResultList() : List<Boolean> {
        return oxQuizList.map{ quiz -> quiz.userAnswer == quiz.correctAnswer }
    }

    fun setCurrentPosition(position : Int) {
        _currentPosition.value = position
    }
}