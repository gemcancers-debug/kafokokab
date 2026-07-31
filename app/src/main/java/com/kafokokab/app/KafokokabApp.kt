/*
نام فایل: KafokokabApp.kt
وظیفه: کلاس Application اصلی پروژه + راه‌اندازی Hilt
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
*/

package com.kafokokab.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * نقطه ورود اصلی اپلیکیشن.
 * با @HiltAndroidApp تمام وابستگی‌های Hilt راه‌اندازی می‌شوند.
 */
@HiltAndroidApp
class KafokokabApp : Application()
