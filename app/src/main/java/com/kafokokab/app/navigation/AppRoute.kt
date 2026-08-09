/*
نام فایل: AppRoute.kt
وظیفه: تعریف مسیرهای type-safe ناوبری اپلیکیشن
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
آخرین تغییر: 2026-08-08 - اضافه شدن مسیر چارت تولد
*/

package com.kafokokab.app.navigation

/**
 * مسیرهای اصلی اپلیکیشن.
 * برای اضافه کردن صفحه جدید فقط یک data object جدید بسازید.
 */
sealed class AppRoute(val route: String) {
    /** صفحه ورود */
    data object Login : AppRoute("login")

    /** صفحه اصلی بعد از ورود کامل */
    data object Home : AppRoute("home")

    /** صفحه نمایش چارت تولد */
    data object BirthChart : AppRoute("birth_chart")

    /** گراف والد آنبوردینگ (برای Shared ViewModel) */
    data object OnboardingGraph : AppRoute("onboarding")

    // ---------- آنبوردینگ (۴ مرحله) ----------
    /** مرحله ۱: اطلاعات تولد */
    data object BirthInfo : AppRoute("onboarding/birth_info")

    /** مرحله ۲: اطلاعات شخصی + کف دست */
    data object PersonalInfo : AppRoute("onboarding/personal_info")

    /** مرحله ۳: اطلاعات تکمیلی (چهره + خال‌شناسی بدون عکس) */
    data object ExtraInfo : AppRoute("onboarding/extra_info")

    /** مرحله ۴: بررسی نهایی */
    data object Review : AppRoute("onboarding/review")
}
