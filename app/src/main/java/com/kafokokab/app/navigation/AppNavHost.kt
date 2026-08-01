/*
نام فایل: AppNavHost.kt
وظیفه: میزبان اصلی Navigation و مدیریت جابه‌جایی بین صفحات
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
آخرین تغییر: 2026-08-01 - اضافه شدن ExtraInfoScreen
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
import com.kafokokab.app.ui.onboarding.ExtraInfoScreen
import com.kafokokab.app.ui.onboarding.PersonalInfoScreen

/**
 * NavHost اصلی اپلیکیشن.
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
        composable(AppRoute.Login.route) {
            LoginScreen(
                onGoogleClick = { navController.navigate(AppRoute.BirthInfo.route) },
                onPhoneClick = { navController.navigate(AppRoute.BirthInfo.route) }
            )
        }

        composable(AppRoute.BirthInfo.route) {
            BirthInfoScreen(
                onBack = { navController.popBackStack() },
                onContinue = { navController.navigate(AppRoute.PersonalInfo.route) }
            )
        }

        composable(AppRoute.PersonalInfo.route) {
            PersonalInfoScreen(
                onBack = { navController.popBackStack() },
                onContinue = { navController.navigate(AppRoute.ExtraInfo.route) },
                onSkipPalm = { navController.navigate(AppRoute.ExtraInfo.route) }
            )
        }

        composable(AppRoute.ExtraInfo.route) {
            ExtraInfoScreen(
                onBack = { navController.popBackStack() },
                onContinue = {
                    // فعلاً به Home می‌رویم تا ReviewScreen ساخته شود
                    navController.navigate(AppRoute.Home.route) {
                        popUpTo(AppRoute.Login.route) { inclusive = true }
                    }
                },
                onSkip = {
                    navController.navigate(AppRoute.Home.route) {
                        popUpTo(AppRoute.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoute.Home.route) {
            HomeScreen()
        }
    }
}
