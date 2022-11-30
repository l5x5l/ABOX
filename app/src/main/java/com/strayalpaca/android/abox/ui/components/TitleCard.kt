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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import com.strayalpaca.android.abox.util.changeColorStringToInt
import com.strayalpaca.android.abox.util.getTextColorByBackground

@Composable
fun TitleCard(imageWidth: Int = 255, symbolColor : String = "#402B25") {

    val symbolColorInt = changeColorStringToInt(symbolColor)
    val textColor = getTextColorByBackground(symbolColorInt)

    ConstraintLayout(modifier = Modifier.wrapContentSize()) {
        val (card, title) = createRefs()

        Surface(
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
            modifier = Modifier
                .padding(2.dp)
                .constrainAs(card) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.surface)
                    .padding(12.dp)
            ) {
                Text(text = "A", fontSize = 38.sp)
                Box(
                    Modifier
                        .padding(vertical = 19.dp)
                        .height((imageWidth * 2).dp)
                        .border(1.dp, MaterialTheme.colors.onSurface)
                ) {
                    // 이미지 부분
                    Box(
                        modifier = Modifier
                            .width(imageWidth.dp)
                            .height((imageWidth * 2).dp)
                            .background(MaterialTheme.colors.secondary)
                    ) {

                    }
                }
                Text(text = "B", fontSize = 38.sp, modifier = Modifier.align(Alignment.Bottom))
            }
        }

        Column(
            modifier = Modifier
                .zIndex(1f)
                .background(color = Color(symbolColorInt))
                .constrainAs(title){
                    start.linkTo(card.start)
                    end.linkTo(card.end)
                    bottom.linkTo(card.bottom, margin = (imageWidth * 0.4).dp)
                    width = Dimension.fillToConstraints
                }
        ) {
            Text(text = "카테고리 테스트", modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.h2, color = Color(textColor))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "돌아가기", modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.h3, color = Color(textColor))
                Text(text = "시작하기", modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.h3, color = Color(textColor))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TitleCardPreview() {
    ABOXTheme {
        Surface(Modifier.fillMaxSize()) {
            TitleCard()
        }
    }
}