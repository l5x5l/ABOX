package com.strayalpaca.android.abox.util

import android.graphics.Color
import androidx.core.graphics.ColorUtils

fun changeColorStringToInt(colorString: String): Int {
    return Color.parseColor(colorString)
}

fun getTextColorByBackground(backgroundColor: Int) : Int {
    return if (ColorUtils.calculateContrast(backgroundColor, Color.WHITE) > ColorUtils.calculateContrast(backgroundColor, Color.BLACK)) {
        Color.WHITE
    } else {
        Color.BLACK
    }
}