/*
نام فایل: AstrologyModule.kt
مسیر: core/core-data/.../di/
وظیفه: تزریق وابستگی موتور نجوم
نویسنده: AI Principal Engineer
تاریخ: 2026-08-08

فعلاً StubAstrologyCalculator را به عنوان پیاده‌سازی پیش‌فرض تزریق می‌کند.
بعداً می‌توان با یک پیاده‌سازی واقعی جایگزین کرد.
*/

package com.kafokokab.core.data.di

import com.kafokokab.core.data.astrology.StubAstrologyCalculator
import com.kafokokab.core.domain.repository.AstrologyCalculator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AstrologyModule {

    @Binds
    @Singleton
    abstract fun bindAstrologyCalculator(
        impl: StubAstrologyCalculator
    ): AstrologyCalculator
}
