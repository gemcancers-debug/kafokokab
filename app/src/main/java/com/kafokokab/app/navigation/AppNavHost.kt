/*
نام فایل: AppNavHost.kt
وظیفه: میزبان اصلی Navigation و مدیریت جابه‌جایی بین صفحات
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
آخرین تغییر: 2026-08-01 - اضافه شدن BirthInfoScreen به جریان آنبوردینگ
*/

package com.kafokokab.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier.modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kafokokab.app.ui.auth.LoginScreen
import com.kafokokab.app.ui.home.HomeScreen
import com.kafokokab.app.ui.onboarding.BirthInfoScreen

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
        startDestination = AppRoute.Login.route,
        modifier = modifier
    ) {
        // صفحه ورود
        composable(AppRoute.Login.route) {
            LoginScreen(
                onGoogleClick = {
                    // فعلاً به مرحله اطلاعات تولد می‌رویم
                    navController.navigate(AppRoute.BirthInfo.route)
                },
                onPhoneClick = {
                    navController.navigate(AppRoute.BirthInfo.route)
                }
            )
        }

        // مرحله ۱ آنبوردینگ: اطلاعات تولد
        composable(AppRoute.BirthInfo.route) {
            BirthInfoScreen(
                onBack = { navController.popBackStack() },
                onContinue = {
                    // بعداً به PersonalInfo می‌رود
                    // فعلاً مستقیم به Home (تا صفحات بعدی ساخته شوند)
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

        // صفحات بعدی آنبوردینگ در مراحل بعد اضافه می‌شوند
    }
}
