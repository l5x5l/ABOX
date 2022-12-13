package com.strayalpaca.android.abox.ui.screens.oxquiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.android.abox.model.data.DebounceLoadingDialogState
import com.strayalpaca.android.abox.ui.components.swipeStack.SwipeStackListener
import com.strayalpaca.android.domain.model.OXQuizItem
import com.strayalpaca.android.domain.model.SwipeOrientation
import com.strayalpaca.android.domain.usecase.UseCaseGetOxQuiz
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OxQuizViewModel @Inject constructor(
    private val useCaseGetOxQuiz: UseCaseGetOxQuiz
) : ViewModel(), SwipeStackListener<OXQuizItem> {
    val oxQuizItemList = mutableListOf<OXQuizItem>()

    private val _remainOxQuizItemList = MutableStateFlow<List<OXQuizItem>>(listOf())
    val remainOxQuizItemList = _remainOxQuizItemList.asStateFlow()

    private val loadingState = DebounceLoadingDialogState(viewModelScope)
    val loadingDialogShow = loadingState.getLoadingStateFlow()

    // onCreate 에 이 함수를 호출할 예정인데, 만약 oxQuizItem 이 이미 로드된 상태에서 다크모드전환같은 작업을 수행하는 경우
    // 다시 oxQuizItem 가 로드되고 관련 state 가 초기화된다.
    // 따라서 oxQuizItemList 가 비어있는지를 통해 이미 oxQuizItem 을 로드한건 아닌지를 확인한다.
    fun getOxQuiz(categoryIndex : Int) {
        if (oxQuizItemList.isEmpty()) {
            viewModelScope.launch {
                loadingState.setLoadingDialogStateDebounce(debounceTime = 500, isShow = true)
                val result = useCaseGetOxQuiz(oxQuizCategoryIndex = categoryIndex)
                loadingState.setLoadingDialogState(isShow = false)

                oxQuizItemList.addAll(result)
                _remainOxQuizItemList.value = result
            }
        }
    }

    override fun onSwipeToLeft(item: OXQuizItem) {
        item.submitAnswer(answer = true)
        val temp = _remainOxQuizItemList.value.toMutableList()
        temp.remove(item)
        _remainOxQuizItemList.value = temp
    }

    override fun onSwipeToRight(item: OXQuizItem) {
        item.submitAnswer(answer = false)
        val temp = _remainOxQuizItemList.value.toMutableList()
        temp.remove(item)
        _remainOxQuizItemList.value = temp
    }

    override fun onStackEmpty() {

    }

    override fun onSwipeAnimationStart(swipeOrientation: SwipeOrientation) {

    }

}