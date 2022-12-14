package com.strayalpaca.android.abox.ui.screens.oxquiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.strayalpaca.android.abox.model.data.DebounceLoadingDialogState
import com.strayalpaca.android.abox.ui.components.swipeStack.SwipeStackListener
import com.strayalpaca.android.domain.model.OXQuizItem
import com.strayalpaca.android.domain.model.SwipeOrientation
import com.strayalpaca.android.domain.usecase.UseCaseGetOxQuiz
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OxQuizViewModel @AssistedInject constructor(
    private val useCaseGetOxQuiz: UseCaseGetOxQuiz,
    @Assisted private val oxQuizCategoryIdx: Int
) : ViewModel(), SwipeStackListener<OXQuizItem> {

    @AssistedFactory
    interface OxQuizAssistedFactory {
        fun create(int: Int): OxQuizViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: OxQuizAssistedFactory,
            oxQuizCategoryIdx: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(oxQuizCategoryIdx) as T
            }
        }
    }

    val oxQuizItemList = mutableListOf<OXQuizItem>()

    private val _remainOxQuizItemList = MutableStateFlow<List<OXQuizItem>>(listOf())
    val remainOxQuizItemList = _remainOxQuizItemList.asStateFlow()

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition = _currentPosition.asStateFlow()

    private val loadingState = DebounceLoadingDialogState(viewModelScope)
    val loadingDialogShow = loadingState.getLoadingStateFlow()

    private val _swipeOrientation = MutableSharedFlow<SwipeOrientation>()
    val swipeOrientation = _swipeOrientation.asSharedFlow()

    init {
        getOxQuiz()
    }

    fun getOxQuiz() {
        viewModelScope.launch {
            loadingState.setLoadingDialogStateDebounce(debounceTime = 500, isShow = true)
            val result = useCaseGetOxQuiz(oxQuizCategoryIndex = oxQuizCategoryIdx)
            loadingState.setLoadingDialogState(isShow = false)

            oxQuizItemList.addAll(result)
            _remainOxQuizItemList.value = result
        }
    }

    override fun onSwipeToLeft(item: OXQuizItem) {
        item.submitAnswer(answer = true)
        val temp = _remainOxQuizItemList.value.toMutableList()
        temp.remove(item)
        _remainOxQuizItemList.value = temp
        _currentPosition.value++
    }

    override fun onSwipeToRight(item: OXQuizItem) {
        item.submitAnswer(answer = false)
        val temp = _remainOxQuizItemList.value.toMutableList()
        temp.remove(item)
        _remainOxQuizItemList.value = temp
        _currentPosition.value++
    }

    override fun onStackEmpty() {

    }

    override fun onSwipeAnimationStart(swipeOrientation: SwipeOrientation) {
        viewModelScope.launch {
            _swipeOrientation.emit(swipeOrientation)
        }
    }

}