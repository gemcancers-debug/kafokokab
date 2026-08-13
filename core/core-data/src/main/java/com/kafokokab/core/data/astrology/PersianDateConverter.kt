/*
نام فایل: PersianDateConverter.kt
مسیر: core/core-data/.../astrology/
وظیفه: تبدیل تاریخ شمسی (جلالی) به میلادی و برعکس
نویسنده: AI Principal Engineer
تاریخ: 2026-08-13

الگوریتم استاندارد تبدیل جلالی ↔ گریگوری.
داده واقعی کاربر ایرانی معمولاً شمسی است؛ محاسبه نجومی روی میلادی انجام می‌شود.
*/

package com.kafokokab.core.data.astrology

/**
 * تاریخ ساده میلادی یا شمسی.
 */
data class CivilDate(val year: Int, val month: Int, val day: Int)

/**
 * تبدیل تاریخ شمسی به میلادی.
 * منبع الگوریتم: تبدیل‌های استاندارد جلالی (مورد استفاده در تقویم‌های ایرانی).
 */
object PersianDateConverter {

    /**
     * تشخیص اینکه سال احتمالاً شمسی است (محدوده معمول کاربران ایرانی).
     */
    fun looksLikeJalaliYear(year: Int): Boolean = year in 1200..1500

    /**
     * تبدیل جلالی → میلادی.
     */
    fun jalaliToGregorian(jy: Int, jm: Int, jd: Int): CivilDate {
        val jy0 = jy - 979
        val jm0 = jm - 1
        val jd0 = jd - 1

        var days = 365 * jy0 + (jy0 / 33) * 8 + ((jy0 % 33) + 3) / 4
        for (i in 0 until jm0) {
            days += if (i < 6) 31 else 30
        }
        days += jd0

        var gDay = days + 79

        var gy = 1600 + 400 * (gDay / 146097)
        gDay %= 146097

        var leap = true
        if (gDay >= 36525) {
            gDay--
            gy += 100 * (gDay / 36524)
            gDay %= 36524
            if (gDay >= 365) gDay++ else leap = false
        }

        gy += 4 * (gDay / 1461)
        gDay %= 1461

        if (gDay >= 366) {
            leap = false
            gDay--
            gy += gDay / 365
            gDay %= 365
        }

        val salA = intArrayOf(
            0, 31,
            if (leap) 29 else 28,
            31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        )
        var gm = 0
        while (gm < 13 && gDay >= salA[gm]) {
            gDay -= salA[gm]
            gm++
        }
        return CivilDate(gy, gm, gDay + 1)
    }

    /**
     * اگر سال شمسی باشد تبدیل می‌کند؛ وگرنه همان تاریخ را برمی‌گرداند.
     */
    fun toGregorianIfNeeded(year: Int, month: Int, day: Int): CivilDate {
        return if (looksLikeJalaliYear(year)) {
            jalaliToGregorian(year, month.coerceIn(1, 12), day.coerceIn(1, 31))
        } else {
            CivilDate(year, month.coerceIn(1, 12), day.coerceIn(1, 31))
        }
    }
}
