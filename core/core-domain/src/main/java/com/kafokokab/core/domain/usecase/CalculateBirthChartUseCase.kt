/*
نام فایل: CalculateBirthChartUseCase.kt
مسیر: core/core-domain/.../usecase/
وظیفه: Use Case محاسبه چارت تولد
نویسنده: AI Principal Engineer
تاریخ: 2026-08-08

این Use Case منطق کسب‌وکار را از ViewModel جدا می‌کند.
*/

package com.kafokokab.core.domain.usecase

import com.kafokokab.core.domain.model.astrology.BirthChart
import com.kafokokab.core.domain.model.astrology.ChartSystem
import com.kafokokab.core.domain.repository.AstrologyCalculator
import javax.inject.Inject

/**
 * Use Case برای محاسبه چارت تولد.
 * ViewModel فقط این کلاس را صدا می‌زند.
 */
class CalculateBirthChartUseCase @Inject constructor(
    private val calculator: AstrologyCalculator
) {
    suspend operator fun invoke(
        year: Int,
        month: Int,
        day: Int,
        hour: Int = 12,
        minute: Int = 0,
        latitude: Double = 35.6892,   // پیش‌فرض تهران
        longitude: Double = 51.3890,
        system: ChartSystem = ChartSystem.WESTERN
    ): BirthChart {
        return calculator.calculateBirthChart(
            year = year,
            month = month,
            day = day,
            hour = hour,
            minute = minute,
            latitude = latitude,
            longitude = longitude,
            system = system
        )
    }
}
