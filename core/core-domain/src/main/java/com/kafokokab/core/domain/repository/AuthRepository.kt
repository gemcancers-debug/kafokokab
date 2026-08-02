/*
نام فایل: AuthRepository.kt
مسیر: core/core-domain/.../repository/
وظیفه: اینترفیس Repository برای احراز هویت (لایه Domain)
نویسنده: AI Principal Engineer
تاریخ: 2026-08-02
*/

package com.kafokokab.core.domain.repository

import com.kafokokab.core.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow

/**
 * قرارداد احراز هویت.
 * پیاده‌سازی واقعی در لایه Data قرار دارد.
 */
interface AuthRepository {

    /** جریان وضعیت کاربر فعلی (null یعنی خارج شده) */
    val currentUser: Flow<AuthUser?>

    /** ورود با اکانت گوگل با استفاده از idToken */
    suspend fun signInWithGoogle(idToken: String): Result<AuthUser>

    /** خروج از حساب */
    suspend fun signOut()

    /** آیا کاربر الان وارد شده؟ */
    fun isUserLoggedIn(): Boolean
}
