package com.strayalpaca.android.abox.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import com.strayalpaca.android.abox.util.conditional

@Composable
fun ListResultDot(
    modifier: Modifier = Modifier,
    amountOfItem: Int,
    currentPosition: Int,
    answerResultList: List<Boolean>
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 0 until amountOfItem step (8)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (j in 0 until 8) {
                    val index = i + j
                    if (index < amountOfItem) {
                        key("${amountOfItem}_${index}") {
                            ResultDot(
                                isCurrentPosition = index == currentPosition,
                                isCorrect = answerResultList[index]
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ResultDot(isCurrentPosition: Boolean = false, isCorrect : Boolean = false) {
    val backgroundColor = if (isCorrect) Color(0xFF59B387) else Color(0xFFD34E4E)
    val strokeColor = MaterialTheme.colors.onSurface

    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .conditional(isCurrentPosition) {
                this.border(width = 2.dp, color = strokeColor, shape = CircleShape)
            }
    )
}

@Composable
@Preview("ResultDotListPreview")
fun ResultDotListPreview() {
    val temp = listOf(true, false, true, false, false, false, true, true, false, true, false, false)
    ABOXTheme {
        Box {
            ListResultDot(amountOfItem = temp.size, currentPosition = 9, answerResultList = temp)
        }
    }
}