package com.strayalpaca.android.abox.model.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DebounceLoadingDialogState(private val coroutineScope: CoroutineScope) {
    private val _isShowLoadingDialog = MutableStateFlow(false)

    private var loadingDialogDebounceJob : Job ?= null

    fun getLoadingStateFlow() = _isShowLoadingDialog.asStateFlow()

    suspend fun setLoadingDialogStateDebounce(debounceTime : Long = 500L, isShow: Boolean){
        loadingDialogDebounceJob?.cancel()
        loadingDialogDebounceJob = coroutineScope.launch {
            delay(debounceTime)
            _isShowLoadingDialog.value = isShow
        }
    }

    fun setLoadingDialogState(isShow : Boolean) {
        loadingDialogDebounceJob?.cancel()
        _isShowLoadingDialog.value = isShow
    }

}