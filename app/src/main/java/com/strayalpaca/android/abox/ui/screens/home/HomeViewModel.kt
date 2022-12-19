package com.strayalpaca.android.abox.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.android.abox.model.data.MutableEventFlow
import com.strayalpaca.android.abox.model.data.asEventFlow
import com.strayalpaca.android.domain.model.Post
import com.strayalpaca.android.domain.model.PostType
import com.strayalpaca.android.domain.usecase.UseCaseGetAvsB
import com.strayalpaca.android.domain.usecase.UseCaseGetHomeData
import com.strayalpaca.android.domain.usecase.UseCaseGetOxQuizCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCaseGetHomeData: UseCaseGetHomeData,
    private val useCaseGetOxQuizCategory: UseCaseGetOxQuizCategory,
    private val useCaseGetAvsB: UseCaseGetAvsB
): ViewModel() {
    private val _popularPost = MutableStateFlow<List<Post>>(listOf())
    val popularPost = _popularPost.asStateFlow()

    private val _recentPost = MutableStateFlow<List<Post>>(listOf())
    val recentPost = _recentPost.asStateFlow()

    private val _randomPost = MutableEventFlow<Post>()
    val randomPost = _randomPost.asEventFlow()

    fun getHomeData() {
        viewModelScope.launch {
            val result = useCaseGetHomeData()
            _popularPost.value = result.popularPost
            _recentPost.value = result.recentPost
        }
    }

    fun getRandomPost() {
        viewModelScope.launch {
            val post = when (PostType.getRandomType()) {
                PostType.AB -> {
                    useCaseGetAvsB.getRandom()
                }
                PostType.OX -> {
                    useCaseGetOxQuizCategory.getRandom()
                }
            }
            _randomPost.emit(post)
        }
    }
}