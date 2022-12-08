package com.strayalpaca.android.abox.ui.screens.avsb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.android.abox.ui.components.swipeStack.SwipeStackListener
import com.strayalpaca.android.domain.model.AB
import com.strayalpaca.android.domain.model.AvsB
import com.strayalpaca.android.domain.model.AvsBContent
import com.strayalpaca.android.domain.usecase.UseCaseGetAvsB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvsBViewModel @Inject constructor(
    private val useCaseGetAvsB: UseCaseGetAvsB
) : ViewModel(), SwipeStackListener<AvsBContent> {
    private val _ab = MutableStateFlow<AvsB?>(null)
    val ab = _ab.asStateFlow()

    fun getAvsB() {
        viewModelScope.launch {
            val result = useCaseGetAvsB(1)
            _ab.value = result
        }
    }

    override fun onSwipeToLeft(item: AvsBContent) {
        item.vote = AB.A
        ab.value!!.addVoteData(item)
    }

    override fun onSwipeToRight(item: AvsBContent) {
        item.vote = AB.B
        ab.value!!.addVoteData(item)
    }

    override fun onStackEmpty() {
        ab.value!!.createNextRoundContent()
    }
}