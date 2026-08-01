/*
نام فایل: AppNavHost.kt
وظیفه: میزبان اصلی Navigation و مدیریت جابه‌جایی بین صفحات
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
آخرین تغییر: 2026-08-01 - اضافه شدن LoginScreen به عنوان صفحه شروع
*/

package com.kafokokab.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier.modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kafokokab.app.ui.auth.LoginScreen
import com.kafokokab.app.ui.home.HomeScreen

/**
 * NavHost اصلی اپلیکیشن.
 * تمام صفحات از اینجا مدیریت می‌شوند.
 *
 * نکته: فعلاً Login به عنوان startDestination تنظیم شده است.
 * بعد از پیاده‌سازی منطق احراز هویت، می‌توان بر اساس وضعیت ورود کاربر
 * بین Login و Home تصمیم گرفت.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Login.route,
        modifier = modifier
    ) {
        // صفحه ورود
        composable(AppRoute.Login.route) {
            LoginScreen(
                onGoogleClick = {
                    // فعلاً مستقیم به Home می‌رویم (منطق واقعی بعداً اضافه می‌شود)
                    navController.navigate(AppRoute.Home.route) {
                        popUpTo(AppRoute.Login.route) { inclusive = true }
                    }
                },
                onPhoneClick = {
                    // فعلاً مستقیم به Home می‌رویم
                    navController.navigate(AppRoute.Home.route) {
                        popUpTo(AppRoute.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // صفحه اصلی
        composable(AppRoute.Home.route) {
            HomeScreen()
        }

        // صفحات آنبوردینگ در مراحل بعدی اینجا اضافه می‌شوند
    }
}
