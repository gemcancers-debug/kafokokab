/*
نام فایل: PlanetPosition.kt
مسیر: core/core-domain/.../model/astrology/
وظیفه: نگهداری موقعیت یک سیاره در چارت
نویسنده: AI Principal Engineer
تاریخ: 2026-08-06

این کلاس نتیجه محاسبه موتور نجوم است و در UI نمایش داده می‌شود.
*/

package com.kafokokab.core.domain.model.astrology

/**
 * موقعیت یک سیاره یا نقطه در چارت تولد.
 *
 * @param planet سیاره یا نقطه
 * @param longitude درجه مطلق در دایره‌البروج (۰ تا ۳۶۰)
 * @param latitude عرض دایره‌البروجی (معمولاً نزدیک صفر)
 * @param speed سرعت حرکت روزانه (درجه در روز)
 * @param isRetrograde آیا رجعی است؟
 * @param house شماره خانه (۱ تا ۱۲) – بعداً محاسبه می‌شود
 */
data class PlanetPosition(
    val planet: Planet,
    val longitude: Double,
    val latitude: Double = 0.0,
    val speed: Double = 0.0,
    val isRetrograde: Boolean = false,
    val house: Int? = null
) {
    /** برج فعلی سیاره */
    val sign: ZodiacSign
        get() = ZodiacSign.fromDegree(longitude)

    /** درجه داخل برج (۰ تا ۳۰) */
    val degreeInSign: Double
        get() = longitude % 30.0

    /** نمایش خوانا برای UI فارسی */
    fun toPersianDisplay(): String {
        val deg = degreeInSign.toInt()
        val min = ((degreeInSign - deg) * 60).toInt()
        val retro = if (isRetrograde) " (رجعی)" else ""
        return "${planet.persianName} در ${sign.persianName} $deg°$min′$retro"
    }
}
