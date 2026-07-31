/*
نام فایل: AppNavHost.kt
وظیفه: میزبان اصلی Navigation و مدیریت جابه‌جایی بین صفحات
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
*/

package com.kafokokab.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier.modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kafokokab.app.ui.home.HomeScreen

/**
 * NavHost اصلی اپلیکیشن.
 * تمام صفحات از اینجا مدیریت می‌شوند.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Home.route,
        modifier = modifier
    ) {
        composable(AppRoute.Home.route) {
            HomeScreen()
        }

        // صفحات بعدی در فازهای بعد اینجا اضافه می‌شوند
    }
}
