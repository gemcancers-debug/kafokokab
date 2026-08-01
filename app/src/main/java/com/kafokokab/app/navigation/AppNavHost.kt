/*
نام فایل: AppNavHost.kt
وظیفه: میزبان اصلی Navigation و مدیریت جابه‌جایی بین صفحات
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
آخرین تغییر: 2026-08-01 - اضافه شدن PersonalInfoScreen به جریان آنبوردینگ
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
import com.kafokokab.app.ui.onboarding.PersonalInfoScreen

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
                    navController.navigate(AppRoute.BirthInfo.route)
                },
                onPhoneClick = {
                    navController.navigate(AppRoute.BirthInfo.route)
                }
            )
        }

        // مرحله ۱: اطلاعات تولد
        composable(AppRoute.BirthInfo.route) {
            BirthInfoScreen(
                onBack = { navController.popBackStack() },
                onContinue = {
                    navController.navigate(AppRoute.PersonalInfo.route)
                }
            )
        }

        // مرحله ۲: اطلاعات شخصی + کف دست
        composable(AppRoute.PersonalInfo.route) {
            PersonalInfoScreen(
                onBack = { navController.popBackStack() },
                onContinue = {
                    // فعلاً به Home می‌رویم تا صفحات بعدی ساخته شوند
                    // بعداً به ExtraInfo تغییر می‌کند
                    navController.navigate(AppRoute.Home.route) {
                        popUpTo(AppRoute.Login.route) { inclusive = true }
                    }
                },
                onSkipPalm = {
                    // رد کردن موقت عکس کف دست و رفتن به مرحله بعد
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

        // صفحات ExtraInfo و Review در مراحل بعدی اضافه می‌شوند
    }
}
