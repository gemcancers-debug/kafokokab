/*
نام فایل: ProfileRepository.kt
مسیر: core/core-domain/.../repository/
وظیفه: رابط ذخیره و بازیابی پروفایل کاربر
نویسنده: AI Principal Engineer
تاریخ: 2026-08-09

این رابط در لایه Domain قرار دارد تا وابستگی به Android نداشته باشد.
*/

package com.kafokokab.core.domain.repository

import com.kafokokab.core.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

/**
 * Repository برای مدیریت پروفایل کاربر.
 */
interface ProfileRepository {

    /** جریان پروفایل فعلی (برای مشاهده در UI) */
    val profileFlow: Flow<UserProfile>

    /** ذخیره کامل پروفایل */
    suspend fun saveProfile(profile: UserProfile)

    /** خواندن یک‌باره پروفایل */
    suspend fun getProfile(): UserProfile

    /** پاک کردن پروفایل (برای خروج از حساب) */
    suspend fun clearProfile()
}
