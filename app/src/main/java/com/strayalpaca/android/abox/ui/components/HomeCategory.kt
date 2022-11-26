package com.strayalpaca.android.abox.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalpaca.android.abox.ui.theme.ABOXTheme

@Composable
fun HomeCategory(categoryTitle: String, categoryDescription: String) {


    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, MaterialTheme.colors.onBackground),
    ) {
        Column(
            modifier = Modifier
                .aspectRatio(1f)
                .clickable {

                }
                .background(MaterialTheme.colors.background)
        ) {
            Text(
                text = categoryTitle,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 14.dp, top = 18.dp)
            )
            Text(
                text = categoryDescription,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 14.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Preview(name = "dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {

    ABOXTheme {
        Box(modifier = Modifier.width(360.dp)) {
            Column(
                modifier = Modifier
                    .width(360.dp)
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    Box(modifier = Modifier
                        .weight(1f)
                        .shadow(16.dp, shape = RoundedCornerShape(16.dp), clip = false)) {
                        HomeCategory("AvsB", "마지막 하나가 남을 때까지!")
                    }
                    Box(modifier = Modifier
                        .weight(1f)
                        .shadow(32.dp, shape = RoundedCornerShape(16.dp), clip = false)) {
                        HomeCategory("AvsB", "마지막 하나가 남을 때까지!")
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Box(modifier = Modifier
                        .weight(1f)
                        .shadow(16.dp, shape = RoundedCornerShape(16.dp), clip = true)) {
                        HomeCategory("AvsB", "마지막 하나가 남을 때까지!")
                    }
                    Box(modifier = Modifier
                        .weight(1f)
                        .shadow(32.dp, shape = RoundedCornerShape(16.dp), clip = true)) {
                        HomeCategory("AvsB", "마지막 하나가 남을 때까지!")
                    }
                }
            }




    //        Surface(
    //            modifier = Modifier
    //                .offset(y = (20).dp, x = (7).dp)
    //                .zIndex(-1f)
    //                .width(422.dp)
    //                .aspectRatio(1f),
    //            shape = CircleShape
    //        ) {
    //            Box(
    //                modifier = Modifier
    //                    .background(Color(0x12000000))
    //            )
    //        }



        }
    }

}

