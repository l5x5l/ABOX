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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.strayalpaca.android.abox.ui.shapes.ImageInCardShape
import com.strayalpaca.android.domain.model.AvsBContent

@Composable
fun AvsBCard(modifier: Modifier = Modifier, imageSizeDp: Int = 225, AvsB : AvsBContent) {
    val heightDiff = 36

    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
        modifier = modifier
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
                AsyncImage(
                    model = AvsB.A.imageUrl,
                    contentDescription = "Image A",
                    modifier = Modifier
                        .offset(x = 0.dp, y = 0.dp)
                        .width(imageSizeDp.dp)
                        .height((imageSizeDp + heightDiff / 2).dp)
                        .graphicsLayer {
                            shape = ImageInCardShape(heightDiff.dp.roundToPx(), isTop = true)
                            clip = true
                        },
                    contentScale = ContentScale.Crop
                )
                AsyncImage(
                    model = AvsB.B.imageUrl,
                    contentDescription = "Image B",
                    modifier = Modifier
                        .offset(x = 0.dp, y = (imageSizeDp - heightDiff / 2).dp)
                        .width(imageSizeDp.dp)
                        .height((imageSizeDp + heightDiff / 2).dp)
                        .graphicsLayer {
                            shape = ImageInCardShape(heightDiff.dp.roundToPx(), isTop = false)
                            clip = true
                        },
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = "B", fontSize = 38.sp, modifier = Modifier.align(Alignment.Bottom))
        }
    }
}