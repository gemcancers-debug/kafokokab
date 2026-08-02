/*
نام فایل: AuthUser.kt
مسیر: core/core-domain/.../model/
وظیفه: مدل دامنه کاربر احراز هویت شده
نویسنده: AI Principal Engineer
تاریخ: 2026-08-02
*/

package com.kafokokab.core.domain.model

/**
 * اطلاعات کاربر بعد از ورود موفق.
 * این مدل مستقل از Firebase است تا لایه Domain تمیز بماند.
 */
data class AuthUser(
    val uid: String,
    val displayName: String?,
    val email: String?,
    val photoUrl: String?
)
