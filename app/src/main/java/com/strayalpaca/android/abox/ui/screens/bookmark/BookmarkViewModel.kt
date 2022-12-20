package com.strayalpaca.android.abox.ui.screens.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.strayalpaca.android.abox.paging.post.PostPagingSource
import com.strayalpaca.android.domain.usecase.UseCaseGetAvsBList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val useCaseGetAvsBList: UseCaseGetAvsBList
) : ViewModel() {
    val postPager = Pager(
        PagingConfig(
            pageSize = 10
        )
    ) {
        PostPagingSource(useCaseGetAvsBList::getBookmark)
    }.flow.cachedIn(viewModelScope)
}