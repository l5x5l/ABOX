package com.strayalpaca.android.abox.ui.screens.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.strayalpaca.android.abox.ui.theme.ABOXTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.strayalpaca.android.abox.ui.components.SplashLogo

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ABOXTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        SplashLogo()
                    }
                }
            }
        }

        moveToHome()
    }

    private fun moveToHome() {
        lifecycleScope.launch {
            delay(1500)
//            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
//            startActivity(intent)
        }
    }
}