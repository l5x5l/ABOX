package com.strayalpaca.android.abox.ui.screens.avsb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.strayalpaca.android.abox.model.data.DebounceLoadingDialogState
import com.strayalpaca.android.abox.ui.components.swipeStack.SwipeStackListener
import com.strayalpaca.android.domain.model.AB
import com.strayalpaca.android.domain.model.AvsB
import com.strayalpaca.android.domain.model.AvsBContent
import com.strayalpaca.android.domain.model.SwipeOrientation
import com.strayalpaca.android.domain.usecase.UseCaseGetAvsB
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AvsBViewModel @AssistedInject constructor(
    private val useCaseGetAvsB: UseCaseGetAvsB,
    @Assisted private val abIndex : Int
) : ViewModel(), SwipeStackListener<AvsBContent> {

    @AssistedFactory
    interface AbAssistedFactory {
        fun create(int : Int) : AvsBViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(assistedFactory : AbAssistedFactory, abIndex : Int) : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(abIndex) as T
            }
        }
    }

    lateinit var ab: AvsB

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
            val result = useCaseGetAvsB(abIndex)
            loadingState.setLoadingDialogState(isShow = false)
            ab = result
            ab.createFirstRoundContent()
            _abContentList.value = ab.abContentList
            changeRound()
        }
    }

    override fun onSwipeToLeft(item: AvsBContent) {
        item.vote = AB.A
        ab.addVoteData(item)
        _currentPosition.value += 1
    }

    override fun onSwipeToRight(item: AvsBContent) {
        item.vote = AB.B
        ab.addVoteData(item)
        _currentPosition.value += 1
    }

    override fun onStackEmpty() {
        ab.createNextRoundContent()
        changeRound()
    }

    override fun onSwipeAnimationStart(swipeOrientation: SwipeOrientation) {
        viewModelScope.launch {
            _swipeOrientation.emit(swipeOrientation)
        }
    }

    private fun changeRound() {
        _currentABRound.value = ab.currentRound
        _currentPosition.value = 0
    }
}