package com.strayalpaca.android.abox.paging.post

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.strayalpaca.android.domain.model.AvsB
import com.strayalpaca.android.domain.model.Post
import java.lang.Exception
import kotlin.reflect.KSuspendFunction1

class PostPagingSource(
    private val loadNext: KSuspendFunction1<Int, List<AvsB>>
) : PagingSource<Int, Post>(){
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val pageIndex = params.key ?: 0
            val response = loadNext(pageIndex)
            val nextKey = if (response.isEmpty()) null else pageIndex + 1
            val prevKey = if (pageIndex == 0) null else pageIndex - 1
            LoadResult.Page(data = response, prevKey = prevKey, nextKey = nextKey)
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }
}