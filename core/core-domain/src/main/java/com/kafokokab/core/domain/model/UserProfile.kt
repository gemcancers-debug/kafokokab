/*
نام فایل: UserProfile.kt
مسیر: core/core-domain/.../model/
وظیفه: مدل دامنه اطلاعات کاربر (منبع حقیقت داده‌های آنبوردینگ)
نویسنده: AI Principal Engineer
تاریخ: 2026-08-01

این کلاس فقط داده نگه می‌دارد و هیچ وابستگی به Android ندارد.
بعداً می‌توان آن را در Room یا DataStore ذخیره کرد.
*/

package com.kafokokab.core.domain.model

/**
 * مدل کامل پروفایل کاربر.
 * تمام اطلاعاتی که در آنبوردینگ جمع‌آوری می‌شود اینجا قرار می‌گیرد.
 */
data class UserProfile(
    // ---------- اطلاعات تولد ----------
    val birthDay: String = "",
    val birthMonth: String = "",          // ماه شمسی
    val birthYear: String = "",
    val birthHour: String = "",
    val birthMinute: String = "",
    val isBirthTimeUnknown: Boolean = false,
    val gender: String = "",              // مرد یا زن
    val birthCountry: String = "ایران",
    val birthProvince: String = "",
    val birthCity: String = "",

    // ---------- اطلاعات فردی ----------
    val firstName: String = "",
    val lastName: String = "",
    val motherName: String = "",

    // ---------- تصاویر (فعلاً فقط وضعیت) ----------
    val hasLeftPalmPhoto: Boolean = false,
    val hasRightPalmPhoto: Boolean = false,
    val hasFacePhoto: Boolean = false,

    // ---------- خال‌شناسی (فقط موقعیت‌ها، بدون عکس) ----------
    val selectedMolePositions: Set<String> = emptySet(),

    // ---------- موارد اختیاری ----------
    val eyeColor: String = "",
    val bloodType: String = "",
    val heightCm: String = "",
    val hairColor: String = "",

    // ---------- وضعیت کلی ----------
    val isOnboardingCompleted: Boolean = false
) {
    /** نام کامل کاربر برای نمایش در هدر */
    val fullName: String
        get() = listOf(firstName, lastName)
            .filter { it.isNotBlank() }
            .joinToString(" ")
            .ifBlank { "کاربر" }

    /** آیا اطلاعات تولد حداقل پر شده؟ */
    val hasBasicBirthInfo: Boolean
        get() = birthDay.isNotBlank() && birthMonth.isNotBlank() && birthYear.isNotBlank()
}
