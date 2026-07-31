/*
نام فایل: AppModule.kt
وظیفه: ماژول پایه Hilt برای تزریق وابستگی‌های سطح Application
نویسنده: AI Principal Engineer
تاریخ: 2026-08-01

در حال حاضر خالی است و در فازهای بعدی با Repositoryها، UseCaseها و دیتابیس پر می‌شود.
*/

package com.kafokokab.app.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * ماژول اصلی Hilt در سطح Application.
 * تمام وابستگی‌های سینگلتون (مثل Database، Repository و ...) اینجا تعریف می‌شوند.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // در فازهای بعدی اینجا Providerها اضافه می‌شوند
}
