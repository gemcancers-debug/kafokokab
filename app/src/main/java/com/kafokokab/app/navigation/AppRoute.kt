/*
نام فایل: AppRoute.kt
وظیفه: تعریف مسیرهای type-safe ناوبری اپلیکیشن
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
*/

package com.kafokokab.app.navigation

/**
 * مسیرهای اصلی اپلیکیشن.
 * بعداً با اضافه شدن featureها گسترش پیدا می‌کند.
 */
sealed class AppRoute(val route: String) {
    data object Home : AppRoute("home")
    // مسیرهای بعدی در فازهای بعد اضافه می‌شوند:
    // data object BirthChart : AppRoute("birth_chart")
    // data object Profile : AppRoute("profile")
    // data object Premium : AppRoute("premium")
}
