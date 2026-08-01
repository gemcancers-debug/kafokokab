/*
نام فایل: AppRoute.kt
وظیفه: تعریف مسیرهای type-safe ناوبری اپلیکیشن
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
آخرین تغییر: 2026-08-01 - اضافه شدن مسیر Login و Onboarding
*/

package com.kafokokab.app.navigation

/**
 * مسیرهای اصلی اپلیکیشن.
 * برای اضافه کردن صفحه جدید فقط یک data object جدید بسازید.
 */
sealed class AppRoute(val route: String) {
    /** صفحه ورود */
    data object Login : AppRoute("login")

    /** صفحه اصلی بعد از ورود */
    data object Home : AppRoute("home")

    // مسیرهای آنبوردینگ (در مراحل بعدی اضافه می‌شوند)
    // data object BirthInfo : AppRoute("onboarding/birth_info")
    // data object PersonalInfo : AppRoute("onboarding/personal_info")
    // data object ExtraInfo : AppRoute("onboarding/extra_info")
    // data object Review : AppRoute("onboarding/review")
}
