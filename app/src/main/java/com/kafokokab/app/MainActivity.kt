/*
نام فایل: MainActivity.kt
وظیفه: Activity اصلی و نقطه شروع UI با Jetpack Compose + Navigation
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
*/

package com.kafokokab.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier.modifier
import androidx.navigation.compose.rememberNavController
import com.kafokokab.app.navigation.AppNavHost
import com.kafokokab.core.ui.theme.KafokokabTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity اصلی اپلیکیشن.
 * از اینجا Navigation و تم پروژه شروع می‌شود.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KafokokabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}
