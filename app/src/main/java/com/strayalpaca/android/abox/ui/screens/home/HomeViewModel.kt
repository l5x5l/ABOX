package com.strayalpaca.android.abox.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.android.domain.model.Post
import com.strayalpaca.android.domain.usecase.UseCaseGetHomeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCaseGetHomeData: UseCaseGetHomeData
): ViewModel() {
    private val _popularPost = MutableStateFlow<List<Post>>(listOf())
    val popularPost = _popularPost.asStateFlow()

    private val _recentPost = MutableStateFlow<List<Post>>(listOf())
    val recentPost = _recentPost.asStateFlow()

    fun getHomeData() {
        viewModelScope.launch {
            val result = useCaseGetHomeData()
            _popularPost.value = result.popularPost
            _recentPost.value = result.recentPost
        }
    }
}