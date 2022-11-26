package com.strayalpaca.android.abox.ui.shapes

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class ImageInCardShape(private val pxHeightDiffBetweenLeftRight : Int, private val isTop : Boolean = true) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = if (isTop) drawTopPath(size) else drawBottomPath(size)
        )
    }

    private fun drawTopPath(size : Size) : Path {
        return Path().apply {
            reset()
            lineTo(x = size.width, y = 0f)
            lineTo(x = size.width, y = size.height - pxHeightDiffBetweenLeftRight)
            lineTo(x = 0f, y = size.height)
            lineTo(x = 0f, y = 0f)
            close()
        }
    }

    private fun drawBottomPath(size : Size) : Path {
        return Path().apply {
            reset()
            moveTo(x = 0f, y = pxHeightDiffBetweenLeftRight.toFloat())
            lineTo(x = size.width, y = 0f)
            lineTo(x = size.width, y = size.height)
            lineTo(x = 0f, y = size.height)
            close()
        }
    }

}