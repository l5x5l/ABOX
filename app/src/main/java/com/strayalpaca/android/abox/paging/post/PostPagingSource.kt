package com.strayalpaca.android.abox.paging.post

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.strayalpaca.android.domain.model.Post
import java.lang.Exception

class PostPagingSource(
    private val loadNext: suspend (pageIndex : Int, pageSize : Int) -> List<Post>
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
            val response = loadNext(pageIndex, params.loadSize)
            val nextKey = if (response.isEmpty()) null else pageIndex + 1
            val prevKey = if (pageIndex == 0) null else pageIndex - 1
            LoadResult.Page(data = response, prevKey = prevKey, nextKey = nextKey)
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }
}