/*
نام فایل: AppNavHost.kt
وظیفه: میزبان اصلی Navigation
آخرین تغییر: 2026-08-02 - اتصال ورود موفق به جریان آنبوردینگ
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
import com.kafokokab.app.ui.onboarding.ReviewScreen

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
                onLoginSuccess = {
                    // بعد از ورود موفق به مرحله اطلاعات تولد می‌رویم
                    navController.navigate(AppRoute.BirthInfo.route) {
                        popUpTo(AppRoute.Login.route) { inclusive = true }
                    }
                },
                onPhoneClick = {
                    // فعلاً همان مسیر آنبوردینگ (بعداً OTP اضافه می‌شود)
                    navController.navigate(AppRoute.BirthInfo.route)
                }
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
                onContinue = { navController.navigate(AppRoute.Review.route) },
                onSkip = { navController.navigate(AppRoute.Review.route) }
            )
        }

        composable(AppRoute.Review.route) {
            ReviewScreen(
                onBack = { navController.popBackStack() },
                onConfirm = {
                    navController.navigate(AppRoute.Home.route) {
                        popUpTo(AppRoute.Login.route) { inclusive = true }
                    }
                },
                onEditBirth = {
                    navController.navigate(AppRoute.BirthInfo.route) { launchSingleTop = true }
                },
                onEditPersonal = {
                    navController.navigate(AppRoute.PersonalInfo.route) { launchSingleTop = true }
                },
                onEditPhotos = {
                    navController.navigate(AppRoute.ExtraInfo.route) { launchSingleTop = true }
                },
                onEditOptional = {
                    navController.navigate(AppRoute.ExtraInfo.route) { launchSingleTop = true }
                }
            )
        }

        composable(AppRoute.Home.route) {
            HomeScreen()
        }
    }
}
