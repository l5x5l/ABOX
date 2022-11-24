package com.strayalpaca.android.abox

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ABOXApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}