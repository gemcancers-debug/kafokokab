/*
نام فایل: StubAstrologyCalculator.kt
مسیر: core/core-data/.../astrology/
وظیفه: پیاده‌سازی موقت (Stub) محاسبه چارت برای توسعه UI
نویسنده: AI Principal Engineer
تاریخ: 2026-08-08

توجه مهم:
- این کلاس فقط برای توسعه و تست UI است.
- محاسبات واقعی نجومی ندارد.
- بعداً با پیاده‌سازی واقعی (Swiss Ephemeris یا جدول اپیمر آفلاین) جایگزین می‌شود.
- موقعیت‌ها تقریبی و بر اساس روز سال تولید می‌شوند تا UI خالی نباشد.
*/

package com.kafokokab.core.data.astrology

import com.kafokokab.core.domain.model.astrology.BirthChart
import com.kafokokab.core.domain.model.astrology.ChartSystem
import com.kafokokab.core.domain.model.astrology.Planet
import com.kafokokab.core.domain.model.astrology.PlanetPosition
import com.kafokokab.core.domain.repository.AstrologyCalculator
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs

/**
 * پیاده‌سازی موقت برای ادامه توسعه.
 * موقعیت سیارات را به صورت تقریبی بر اساس روز سال تولید می‌کند.
 */
@Singleton
class StubAstrologyCalculator @Inject constructor() : AstrologyCalculator {

    override suspend fun calculateBirthChart(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        latitude: Double,
        longitude: Double,
        system: ChartSystem
    ): BirthChart {

        // محاسبه تقریبی روز سال (۱ تا ۳۶۵)
        val dayOfYear = approximateDayOfYear(month, day)

        // پایه طول دایره‌البروجی بر اساس روز سال
        val baseLongitude = (dayOfYear * 0.9856) % 360.0   // تقریبی حرکت خورشید

        val positions = mutableListOf<PlanetPosition>()

        // خورشید
        positions.add(
            PlanetPosition(
                planet = Planet.SUN,
                longitude = baseLongitude,
                speed = 0.9856,
                isRetrograde = false
            )
        )

        // ماه (سریع‌تر حرکت می‌کند)
        positions.add(
            PlanetPosition(
                planet = Planet.MOON,
                longitude = (baseLongitude + dayOfYear * 13.176) % 360.0,
                speed = 13.176,
                isRetrograde = false
            )
        )

        // عطارد
        positions.add(
            PlanetPosition(
                planet = Planet.MERCURY,
                longitude = (baseLongitude + 50 + dayOfYear * 1.2) % 360.0,
                speed = 1.2,
                isRetrograde = dayOfYear % 40 < 10
            )
        )

        // زهره
        positions.add(
            PlanetPosition(
                planet = Planet.VENUS,
                longitude = (baseLongitude + 80 + dayOfYear * 0.8) % 360.0,
                speed = 0.8,
                isRetrograde = dayOfYear % 60 < 15
            )
        )

        // مریخ
        positions.add(
            PlanetPosition(
                planet = Planet.MARS,
                longitude = (baseLongitude + 120 + dayOfYear * 0.5) % 360.0,
                speed = 0.5,
                isRetrograde = dayOfYear % 80 < 20
            )
        )

        // مشتری
        positions.add(
            PlanetPosition(
                planet = Planet.JUPITER,
                longitude = (baseLongitude + 160 + dayOfYear * 0.08) % 360.0,
                speed = 0.08,
                isRetrograde = dayOfYear % 120 < 30
            )
        )

        // زحل
        positions.add(
            PlanetPosition(
                planet = Planet.SATURN,
                longitude = (baseLongitude + 200 + dayOfYear * 0.03) % 360.0,
                speed = 0.03,
                isRetrograde = dayOfYear % 150 < 40
            )
        )

        // اورانوس، نپتون، پلوتو (حرکت خیلی کند)
        positions.add(PlanetPosition(Planet.URANUS, (baseLongitude + 240) % 360.0, speed = 0.01))
        positions.add(PlanetPosition(Planet.NEPTUNE, (baseLongitude + 280) % 360.0, speed = 0.006))
        positions.add(PlanetPosition(Planet.PLUTO, (baseLongitude + 310) % 360.0, speed = 0.004))

        // طلوع (Ascendant) - تقریبی بر اساس ساعت و طول جغرافیایی
        val ascLongitude = (baseLongitude + hour * 15.0 + longitude / 15.0) % 360.0
        positions.add(
            PlanetPosition(
                planet = Planet.ASCENDANT,
                longitude = ascLongitude,
                speed = 0.0
            )
        )

        // Midheaven
        positions.add(
            PlanetPosition(
                planet = Planet.MIDHEAVEN,
                longitude = (ascLongitude + 90) % 360.0,
                speed = 0.0
            )
        )

        return BirthChart(
            positions = positions,
            system = system,
            ayanamsa = if (system == ChartSystem.VEDIC) 24.0 else null  // تقریبی لاهیری
        )
    }

    /** محاسبه تقریبی روز سال از ماه و روز */
    private fun approximateDayOfYear(month: Int, day: Int): Int {
        val daysInMonth = intArrayOf(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        var total = day
        for (m in 1 until month.coerceIn(1, 12)) {
            total += daysInMonth[m]
        }
        return total.coerceIn(1, 365)
    }
}
