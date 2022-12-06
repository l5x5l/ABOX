package com.strayalpaca.android.abox.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun getWindowWidth() : Float {
    return with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
}