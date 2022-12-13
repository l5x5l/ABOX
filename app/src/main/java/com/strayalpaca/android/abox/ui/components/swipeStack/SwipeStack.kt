package com.strayalpaca.android.abox.ui.components.swipeStack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.strayalpaca.android.abox.model.const.STACK_BOTTOM_POSITION
import com.strayalpaca.android.abox.ui.components.PostCard
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import com.strayalpaca.android.domain.model.SwipeOrientation

@Composable
fun <T> SwipeStack(
    dataList: List<T>,
    swipeStackListener: SwipeStackListener<T>,
    modifier: Modifier = Modifier,
    content: @Composable (Modifier, T) -> Unit
) {
    for (i: Int in 0..STACK_BOTTOM_POSITION) {
        if (i < dataList.size) {
            content(
                modifier
                    .swipe(swipeStackListener, i, dataList[i], dataList.size == 1)
                    .zIndex(3f - i), dataList[i]
            )
        }
    }
}


@Composable
@Preview("SwipeStackPreview")
fun SwipeStackPreview() {
    val data = remember {
        mutableStateListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    }

    val swipeStackListener = object : SwipeStackListener<Int> {
        override fun onSwipeToLeft(item: Int) {
            data.remove(item)
        }

        override fun onSwipeToRight(item: Int) {
            data.remove(item)
        }

        override fun onStackEmpty() {
            data.addAll(mutableStateListOf(11, 12, 13, 14, 15, 16, 17))
        }

        override fun onSwipeAnimationStart(swipeOrientation: SwipeOrientation) {

        }
    }

    ABOXTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.onBackground)
        ) {
            SwipeStack(
                dataList = data, swipeStackListener = swipeStackListener, modifier = Modifier.align(
                    Alignment.Center
                )
            ) { modifier: Modifier, i: Int ->
                key(i) {
                    PostCard(
                        modifier = modifier
                    )
                }
            }
        }
    }
}