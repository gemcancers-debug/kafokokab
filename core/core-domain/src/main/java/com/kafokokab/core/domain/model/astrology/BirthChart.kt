/*
نام فایل: BirthChart.kt
مسیر: core/core-domain/.../model/astrology/
وظیفه: مدل کامل چارت تولد (غربی / ودیک)
نویسنده: AI Principal Engineer
تاریخ: 2026-08-06

این کلاس نتیجه نهایی موتور محاسبه است.
فعلاً فقط ساختار داده دارد؛ محاسبه واقعی در فازهای بعدی اضافه می‌شود.
*/

package com.kafokokab.core.domain.model.astrology

/**
 * چارت کامل تولد کاربر.
 *
 * @param positions موقعیت همه سیارات و نقاط
 * @param system سیستم محاسباتی (Western یا Vedic)
 * @param ayanamsa برای سیستم ودیک (مقدار پیش‌فرض لاهیری)
 */
data class BirthChart(
    val positions: List<PlanetPosition>,
    val system: ChartSystem = ChartSystem.WESTERN,
    val ayanamsa: Double? = null,          // فقط برای ودیک
    val calculatedAt: Long = System.currentTimeMillis()
) {
    /** موقعیت خورشید */
    val sun: PlanetPosition?
        get() = positions.find { it.planet == Planet.SUN }

    /** موقعیت ماه */
    val moon: PlanetPosition?
        get() = positions.find { it.planet == Planet.MOON }

    /** طلوع (Ascendant) */
    val ascendant: PlanetPosition?
        get() = positions.find { it.planet == Planet.ASCENDANT }

    /** برج خورشید (برای نمایش سریع در UI) */
    val sunSign: ZodiacSign?
        get() = sun?.sign

    /** برج ماه */
    val moonSign: ZodiacSign?
        get() = moon?.sign

    /** برج طلوع */
    val risingSign: ZodiacSign?
        get() = ascendant?.sign
}

/** سیستم محاسبه چارت */
enum class ChartSystem(val persianName: String) {
    WESTERN("غربی (تروپیکال)"),
    VEDIC("ودیک (سیدریال)")
}
