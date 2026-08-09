/*
نام فایل: AppNavHost.kt
وظیفه: میزبان اصلی Navigation
آخرین تغییر: 2026-08-08 - اضافه شدن صفحه چارت تولد
*/

package com.kafokokab.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kafokokab.app.ui.auth.LoginScreen
import com.kafokokab.app.ui.chart.BirthChartScreen
import com.kafokokab.app.ui.home.HomeScreen
import com.kafokokab.app.ui.onboarding.BirthInfoScreen
import com.kafokokab.app.ui.onboarding.ExtraInfoScreen
import com.kafokokab.app.ui.onboarding.OnboardingViewModel
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
                    navController.navigate(AppRoute.OnboardingGraph.route) {
                        popUpTo(AppRoute.Login.route) { inclusive = true }
                    }
                },
                onPhoneClick = {
                    navController.navigate(AppRoute.OnboardingGraph.route)
                }
            )
        }

        // گراف آنبوردینگ – ViewModel مشترک بین همه مراحل
        navigation(
            startDestination = AppRoute.BirthInfo.route,
            route = AppRoute.OnboardingGraph.route
        ) {
            composable(AppRoute.BirthInfo.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(AppRoute.OnboardingGraph.route)
                }
                val viewModel: OnboardingViewModel = hiltViewModel(parentEntry)

                BirthInfoScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onContinue = { navController.navigate(AppRoute.PersonalInfo.route) }
                )
            }

            composable(AppRoute.PersonalInfo.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(AppRoute.OnboardingGraph.route)
                }
                val viewModel: OnboardingViewModel = hiltViewModel(parentEntry)

                PersonalInfoScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onContinue = { navController.navigate(AppRoute.ExtraInfo.route) },
                    onSkipPalm = { navController.navigate(AppRoute.ExtraInfo.route) }
                )
            }

            composable(AppRoute.ExtraInfo.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(AppRoute.OnboardingGraph.route)
                }
                val viewModel: OnboardingViewModel = hiltViewModel(parentEntry)

                ExtraInfoScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onContinue = { navController.navigate(AppRoute.Review.route) },
                    onSkip = { navController.navigate(AppRoute.Review.route) }
                )
            }

            composable(AppRoute.Review.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(AppRoute.OnboardingGraph.route)
                }
                val viewModel: OnboardingViewModel = hiltViewModel(parentEntry)

                ReviewScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onConfirm = {
                        viewModel.completeOnboarding()
                        navController.navigate(AppRoute.Home.route) {
                            popUpTo(AppRoute.OnboardingGraph.route) { inclusive = true }
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
        }

        composable(AppRoute.Home.route) {
            HomeScreen(
                onNavigateToChart = {
                    navController.navigate(AppRoute.BirthChart.route)
                }
            )
        }

        composable(AppRoute.BirthChart.route) {
            BirthChartScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
