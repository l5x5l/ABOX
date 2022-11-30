package com.strayalpaca.android.abox.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

internal fun Context.findActivity() : Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Cannot find Activity in this context")
}