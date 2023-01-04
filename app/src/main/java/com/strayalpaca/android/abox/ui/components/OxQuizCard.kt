package com.strayalpaca.android.abox.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import com.strayalpaca.android.domain.model.OXQuizItem

@Composable
fun OxQuizCard(modifier: Modifier = Modifier, imageSizeDp: Int = 225, oxQuizItem: OXQuizItem) {
    val strokeColor = when(oxQuizItem.userAnswer) {
        (null) -> {
            MaterialTheme.colors.onSurface
        }
        (oxQuizItem.correctAnswer) -> {
            Color(0xFF59B387)
        }
        (!oxQuizItem.correctAnswer) -> {
            Color(0xFFD34E4E)
        }
        else -> {
            throw IllegalStateException("userAnswer must be null or boolean")
        }
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, color = strokeColor),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(12.dp)
        ) {
            Text(text = "O", fontSize = 38.sp, color = strokeColor)
            Column(
                Modifier
                    .padding(vertical = 19.dp)
                    .height((imageSizeDp * 2).dp)
                    .border(1.dp, color = MaterialTheme.colors.onSurface)
            ) {
                AsyncImage(
                    model = oxQuizItem.imageUrl,
                    contentDescription = "Image A",
                    modifier = Modifier
                        .offset(x = 0.dp, y = 0.dp)
                        .width(imageSizeDp.dp)
                        .height(imageSizeDp.dp)
                        .border(1.dp, color = MaterialTheme.colors.onSurface)
                    ,
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = oxQuizItem.quizText,
                    modifier = Modifier
                        .width(imageSizeDp.dp)
                        .height(imageSizeDp.dp)
                        .padding(16.dp)
                    ,
                )
            }
            Text(text = "X", fontSize = 38.sp, modifier = Modifier.align(Alignment.Bottom), color = strokeColor)
        }
    }
}

@Composable
@Preview("OxQuizCard Preview")
fun OxQuizPreview() {
    ABOXTheme {
        Box(Modifier.fillMaxSize()){
            OxQuizCard(oxQuizItem = OXQuizItem(index = 1, imageUrl = null, quizText = "위 사진에서 주로 사용되는 색상 이름인 teal은 쇠오리에서 유래되었다.", correctAnswer = true, totalAmountOfAnswer = 0, totalAmountOfCorrectAnswer = 0, categoryIndex = 1))
        }
    }
}