/*
نام فایل: AstrologyModule.kt
مسیر: core/core-data/.../di/
وظیفه: تزریق وابستگی موتور نجوم و UseCaseها
نویسنده: AI Principal Engineer
تاریخ: 2026-08-08
آخرین تغییر: 2026-08-13 - جایگزینی Stub با FormulaAstrologyCalculator (محاسبه واقعی آفلاین)
*/

package com.kafokokab.core.data.di

import com.kafokokab.core.data.astrology.FormulaAstrologyCalculator
import com.kafokokab.core.domain.repository.AstrologyCalculator
import com.kafokokab.core.domain.usecase.CalculateBirthChartUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AstrologyModule {

    /**
     * موتور واقعی فرمول‌محور (آفلاین) به‌جای Stub.
     */
    @Binds
    @Singleton
    abstract fun bindAstrologyCalculator(
        impl: FormulaAstrologyCalculator
    ): AstrologyCalculator

    companion object {
        @Provides
        @Singleton
        fun provideCalculateBirthChartUseCase(
            calculator: AstrologyCalculator
        ): CalculateBirthChartUseCase {
            return CalculateBirthChartUseCase(calculator)
        }
    }
}
