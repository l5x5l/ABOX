package com.strayalpaca.android.abox.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.strayalpaca.android.domain.model.Post

@Composable
fun PostPagingList(
    posts: LazyPagingItems<Post>,
    modifier: Modifier = Modifier
) {
    when (posts.loadState.refresh) {
        LoadState.Loading -> {
            Log.d("LoadState.Loading", "called")
        }
        is LoadState.Error -> {
            Log.d("LoadState.Error", "called")
        }
        else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(count = posts.itemCount) { index ->
                    posts[index]?.let { PostItem(post = it) }
                }
            }
        }
    }
}