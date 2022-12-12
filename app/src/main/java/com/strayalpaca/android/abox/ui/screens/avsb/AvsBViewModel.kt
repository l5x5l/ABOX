package com.strayalpaca.android.abox.ui.screens.avsb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.android.abox.model.data.DebounceLoadingDialogState
import com.strayalpaca.android.abox.ui.components.swipeStack.SwipeStackListener
import com.strayalpaca.android.domain.model.AB
import com.strayalpaca.android.domain.model.AvsB
import com.strayalpaca.android.domain.model.AvsBContent
import com.strayalpaca.android.domain.model.SwipeOrientation
import com.strayalpaca.android.domain.usecase.UseCaseGetAvsB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvsBViewModel @Inject constructor(
    private val useCaseGetAvsB: UseCaseGetAvsB
) : ViewModel(), SwipeStackListener<AvsBContent> {
    var ab: AvsB? = null

    private val _abContentList = MutableStateFlow<List<AvsBContent>>(listOf())
    val abContentList = _abContentList.asStateFlow()

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition = _currentPosition.asStateFlow()

    private val _currentABRound = MutableStateFlow(0)
    val currentABRound = _currentABRound.asStateFlow()

    private val _swipeOrientation = MutableSharedFlow<SwipeOrientation>()
    val swipeOrientation = _swipeOrientation.asSharedFlow()

    private val loadingState = DebounceLoadingDialogState(viewModelScope)
    val loadingDialogShow = loadingState.getLoadingStateFlow()

    init {
        getAvsB()
    }

    fun getAvsB() {
        viewModelScope.launch {
            loadingState.setLoadingDialogStateDebounce(debounceTime = 500, isShow = true)
            val result = useCaseGetAvsB(1)
            loadingState.setLoadingDialogState(isShow = false)
            ab = result
            ab!!.createFirstRoundContent()
            changeRound()
        }
    }

    override fun onSwipeToLeft(item: AvsBContent) {
        item.vote = AB.A
        ab!!.addVoteData(item)
        _currentPosition.value += 1
    }

    override fun onSwipeToRight(item: AvsBContent) {
        item.vote = AB.B
        ab!!.addVoteData(item)
        _currentPosition.value += 1
    }

    override fun onStackEmpty() {
        ab!!.createNextRoundContent()
        changeRound()
    }

    override fun onSwipeAnimationStart(swipeOrientation: SwipeOrientation) {
        viewModelScope.launch {
            _swipeOrientation.emit(swipeOrientation)
        }
    }

    private fun changeRound() {
        _abContentList.value = ab!!.abContentList
        _currentABRound.value = ab!!.currentRound
        _currentPosition.value = 0
    }
}