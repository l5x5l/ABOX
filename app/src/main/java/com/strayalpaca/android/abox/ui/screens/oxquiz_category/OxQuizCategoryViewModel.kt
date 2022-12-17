package com.strayalpaca.android.abox.ui.screens.oxquiz_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.android.domain.model.OXQuizCategory
import com.strayalpaca.android.domain.usecase.UseCaseGetOxQuizCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OxQuizCategoryViewModel @Inject constructor(
    private val useCaseGetOxQuizCategory: UseCaseGetOxQuizCategory
) : ViewModel() {
    private val _categoryList = MutableStateFlow<List<OXQuizCategory>>(listOf())
    val categoryList = _categoryList.asStateFlow()

    init {
        viewModelScope.launch {
            val result = useCaseGetOxQuizCategory()
            _categoryList.value = result
        }
    }
}