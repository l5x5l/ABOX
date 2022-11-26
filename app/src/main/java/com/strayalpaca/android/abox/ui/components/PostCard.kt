package com.strayalpaca.android.abox.ui.components

import android.content.res.Configuration
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strayalpaca.android.abox.ui.shapes.ImageInCardShape
import com.strayalpaca.android.abox.ui.theme.ABOXTheme

@Composable
fun PostCard(imageSizeDp: Int = 225) {

    val heightDiff = 36

    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
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
                    .height((imageSizeDp * 2).dp)
                    .border(1.dp, MaterialTheme.colors.onSurface)
            ) {
//                AsyncImage(
//                    model = "https://picsum.photos/seed/picsum/500/500",
//                    contentDescription = "Image A",
//                    modifier = Modifier
//                        .offset(x = 0.dp, y = 0.dp)
//                        .width(imageSizeDp.dp)
//                        .height((imageSizeDp + heightDiff / 2).dp)
//                        .graphicsLayer {
//                            shape = ImageInCardShape(heightDiff.dp.roundToPx(), isTop = true)
//                            clip = true
//                        }
//                        .background(MaterialTheme.colors.primary)
//                AsyncImage(
//                    model = "https://picsum.photos/seed/picsum/500/500",
//                    contentDescription = "Image B",
//                    modifier = Modifier
//                        .offset(x = 0.dp, y = 0.dp)
//                        .width(imageSizeDp.dp)
//                        .height((imageSizeDp + heightDiff / 2).dp)
//                        .graphicsLayer {
//                            shape = ImageInCardShape(heightDiff.dp.roundToPx(), isTop = false)
//                            clip = true
//                        }
//                        .background(MaterialTheme.colors.primary)

                Box(
                    modifier = Modifier
                        .offset(x = 0.dp, y = 0.dp)
                        .width(imageSizeDp.dp)
                        .height((imageSizeDp + heightDiff / 2).dp)
                        .graphicsLayer {
                            shape = ImageInCardShape(heightDiff.dp.roundToPx(), isTop = true)
                            clip = true
                        }
                        .background(MaterialTheme.colors.secondary)
                ) {

                }
                Box(
                    modifier = Modifier
                        .offset(x = 0.dp, y = (imageSizeDp - heightDiff / 2).dp)
                        .width(imageSizeDp.dp)
                        .height((imageSizeDp + heightDiff / 2).dp)
                        .graphicsLayer {
                            shape = ImageInCardShape(heightDiff.dp.roundToPx(), isTop = false)
                            clip = true
                        }
                        .background(MaterialTheme.colors.secondaryVariant)
                ) {

                }
            }
            Text(text = "B", fontSize = 38.sp, modifier = Modifier.align(Alignment.Bottom))
        }
    }
}

@Preview(showBackground = true)
@Preview(name = "dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PostCardPreview() {
    ABOXTheme {
        PostCard()
    }
}