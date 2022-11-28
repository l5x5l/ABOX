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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
fun PostItem(symbolColor: String = "#402B25") {
    val symbolColorInt = changeColorStringToInt(symbolColor)
    val textColor = getTextColorByBackground(symbolColorInt)

    ConstraintLayout(modifier = Modifier.wrapContentSize()) {
        val (card, title) = createRefs()

        Surface(
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.6f)
                .constrainAs(card) {

                }
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, MaterialTheme.colors.onSurface)
                )
            }
        }


        Column(
            modifier = Modifier
                .zIndex(1f)
                .background(color = Color(symbolColorInt))
                .constrainAs(title){
                    start.linkTo(card.start)
                    end.linkTo(card.end)
                    bottom.linkTo(card.bottom, margin = 40.dp)
                    width = Dimension.fillToConstraints
                }
        ) {
            Text(text = "카테고리 테스트\n테스트\nxptmxm", modifier = Modifier.padding(12.dp).fillMaxWidth(), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(textColor), maxLines = 2)
        }
    }

}

@Preview
@Composable
fun PostItemPreview() {
    ABOXTheme {
        Column(modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Box(modifier = Modifier.weight(1f)){
                    PostItem()
                }
                Box(modifier = Modifier.weight(1f)){
                    PostItem()
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(modifier = Modifier.weight(1f)){
                    PostItem()
                }
                Box(modifier = Modifier.weight(1f)){
                    PostItem()
                }
            }
        }
    }
}