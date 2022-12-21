package com.strayalpaca.android.abox.ui.screens.oxquiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.strayalpaca.android.abox.model.data.DebounceLoadingDialogState
import com.strayalpaca.android.abox.ui.components.swipeStack.SwipeStackListener
import com.strayalpaca.android.domain.model.OXQuizItem
import com.strayalpaca.android.domain.model.SwipeOrientation
import com.strayalpaca.android.domain.usecase.UseCaseGetOxQuiz
import com.strayalpaca.android.domain.usecase.UseCaseSendOxQuizSolveData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class OxQuizViewModel @AssistedInject constructor(
    private val useCaseGetOxQuiz: UseCaseGetOxQuiz,
    private val useCaseSendOxQuizSolveData: UseCaseSendOxQuizSolveData,
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

    // 이 데이터가 과연 진짜 필요할지 고민해볼것
    private val originalOxQuizItemList = mutableListOf<OXQuizItem>()
    val amountOfQuizItem get() = originalOxQuizItemList.size

    private val solvedOxQuizItemList = mutableListOf<OXQuizItem>()

    private val _remainOxQuizItemList = MutableStateFlow<List<OXQuizItem>>(listOf())
    val remainOxQuizItemList = _remainOxQuizItemList.asStateFlow()

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition = _currentPosition.asStateFlow()

    private val loadingState = DebounceLoadingDialogState(viewModelScope)
    val loadingDialogShow = loadingState.getLoadingStateFlow()

    private val _swipeOrientation = MutableSharedFlow<SwipeOrientation>()
    val swipeOrientation = _swipeOrientation.asSharedFlow()

    private val _isShowErrorDialog = MutableStateFlow(false)
    val isShowErrorDialog = _isShowErrorDialog.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        when(throwable) {
            is IOException -> {
                _isShowErrorDialog.value = true
            }
        }
    }

    init {
        getOxQuiz()
    }

    fun getOxQuiz() {
        viewModelScope.launch {
            loadingState.setLoadingDialogStateDebounce(debounceTime = 500, isShow = true)
            val result = useCaseGetOxQuiz(oxQuizCategoryIndex = oxQuizCategoryIdx)
            loadingState.setLoadingDialogState(isShow = false)

            originalOxQuizItemList.addAll(result)
            _remainOxQuizItemList.value = result
        }
    }

    fun sendSolveData() {
        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            useCaseSendOxQuizSolveData(solvedOxQuizItemList)
        }
    }

    override fun onSwipeToLeft(item: OXQuizItem) {
        item.applyAnswer(answer = true)
        solvedOxQuizItemList.add(item.copy())
        val temp = _remainOxQuizItemList.value.toMutableList()
        temp.remove(item)
        _remainOxQuizItemList.value = temp
    }

    override fun onSwipeToRight(item: OXQuizItem) {
        item.applyAnswer(answer = false)
        solvedOxQuizItemList.add(item.copy())
        val temp = _remainOxQuizItemList.value.toMutableList()
        temp.remove(item)
        _remainOxQuizItemList.value = temp
    }

    override fun onStackEmpty() {

    }

    override fun onSwipeAnimationStart(swipeOrientation: SwipeOrientation) {
        viewModelScope.launch {
            _currentPosition.value += 1
            _swipeOrientation.emit(swipeOrientation)
        }
    }

}