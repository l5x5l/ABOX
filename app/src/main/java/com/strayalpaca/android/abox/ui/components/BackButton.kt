package com.strayalpaca.android.abox.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalpaca.android.abox.R
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import com.strayalpaca.android.abox.util.findActivity

@Composable
fun BackButton(modifier: Modifier = Modifier, backButtonCallback: (() -> Unit)? = null) {
    val context = LocalContext.current.findActivity()
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Image(
        painter = painterResource(id = R.drawable.ic_arrow_back),
        contentDescription = "back button",
        modifier = modifier
            .size(48.dp)
            .padding(12.dp)
            .clickable(
                onClick = {
                    if (backButtonCallback != null) {
                        backButtonCallback.invoke()
                    } else {
                        context.finish()
                    }
                },
                indication = rememberRipple(bounded = false),
                interactionSource = interactionSource
            ),
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
    )
}

@Composable
@Preview("BackButton Preview")
fun BackButtonPreview() {
    ABOXTheme {
        BackButton()
    }
}

