package com.strayalpaca.android.abox.ui.components

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.strayalpaca.android.abox.ui.screens.avsb.AvsBActivity
import com.strayalpaca.android.abox.util.changeColorStringToInt
import com.strayalpaca.android.abox.util.findActivity
import com.strayalpaca.android.abox.util.getTextColorByBackground
import com.strayalpaca.android.domain.model.Post

@Composable
fun PostItem(post: Post) {
    val symbolColorInt = changeColorStringToInt(post.primaryColor)
    val textColor = getTextColorByBackground(symbolColorInt)
    val context = LocalContext.current.findActivity()

    ConstraintLayout(modifier = Modifier.wrapContentSize().clickable {
        val intent = Intent(context, AvsBActivity::class.java)
        context.startActivity(intent)
    }) {
        val (card, title) = createRefs()

        Surface(
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.6f)
                .constrainAs(card) {

                }
                .padding(1.dp)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                AsyncImage(
                    model = post.thumbnailUrl,
                    contentDescription = "thumbnail Image of post",
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, MaterialTheme.colors.onSurface),
                    contentScale = ContentScale.Crop
                )
            }
        }


        Column(
            modifier = Modifier
                .zIndex(1f)
                .background(color = Color(symbolColorInt))
                .constrainAs(title) {
                    start.linkTo(card.start)
                    end.linkTo(card.end)
                    bottom.linkTo(card.bottom, margin = 40.dp)
                    width = Dimension.fillToConstraints
                }
        ) {
            Text(
                text = post.title,
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.h4,
                color = Color(textColor),
                maxLines = 2
            )
        }
    }
}