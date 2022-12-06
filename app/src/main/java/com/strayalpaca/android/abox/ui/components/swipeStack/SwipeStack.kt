package com.strayalpaca.android.abox.ui.components.swipeStack

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.strayalpaca.android.abox.ui.theme.ABOXTheme

@Composable
fun <T> SwipeStack(
    dataList: List<T>,
    swipeStackListener: SwipeStackListener<T>,
    modifier: Modifier = Modifier
) {
    for (i: Int in 0..BOTTOM_POSITION) {
        if (i < dataList.size) {
            key(dataList[i]) {
                Box(
                    modifier = modifier
                        .fillMaxSize(0.8f)
                        .swipe(swipeStackListener, i, dataList[i], dataList.size == 1)
                        .border(1.dp, color = MaterialTheme.colors.onSurface)
                        .background(color = MaterialTheme.colors.surface)
                        .zIndex(3f - i)
                ) {
                    Text(text = dataList[i].toString(), style = MaterialTheme.typography.h1)
                }
            }
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
    }

    ABOXTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.onBackground)
        ) {
            SwipeStack(dataList = data, swipeStackListener = swipeStackListener, modifier = Modifier.align(
                Alignment.Center))
        }
    }
}