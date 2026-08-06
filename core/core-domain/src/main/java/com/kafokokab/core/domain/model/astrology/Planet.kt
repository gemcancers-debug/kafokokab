/*
نام فایل: Planet.kt
مسیر: core/core-domain/.../model/astrology/
وظیفه: تعریف سیارات و نقاط مهم نجومی
نویسنده: AI Principal Engineer
تاریخ: 2026-08-06

نکته برای آینده:
- این enum فقط هویت سیاره را نگه می‌دارد
- محاسبه موقعیت واقعی در لایه Data / Engine انجام می‌شود
- نام‌های فارسی برای نمایش در UI استفاده می‌شوند
*/

package com.kafokokab.core.domain.model.astrology

/**
 * سیارات و نقاط اصلی در طالع‌بینی غربی و ودیک.
 *
 * هر سیاره دارای:
 * - نام انگلیسی (برای محاسبات)
 * - نام فارسی (برای UI)
 * - نماد یونیکد (برای نمایش سریع)
 */
enum class Planet(
    val englishName: String,
    val persianName: String,
    val symbol: String
) {
    SUN("Sun", "خورشید", "☉"),
    MOON("Moon", "ماه", "☽"),
    MERCURY("Mercury", "عطارد", "☿"),
    VENUS("Venus", "زهره", "♀"),
    MARS("Mars", "مریخ", "♂"),
    JUPITER("Jupiter", "مشتری", "♃"),
    SATURN("Saturn", "زحل", "♄"),
    URANUS("Uranus", "اورانوس", "♅"),
    NEPTUNE("Neptune", "نپتون", "♆"),
    PLUTO("Pluto", "پلوتو", "♇"),

    // نقاط مهم
    ASCENDANT("Ascendant", "طلوع", "Asc"),
    MIDHEAVEN("Midheaven", "وسط‌السماء", "MC"),
    NORTH_NODE("North Node", "گره شمالی", "☊"),
    SOUTH_NODE("South Node", "گره جنوبی", "☋");

    companion object {
        /** لیست سیارات اصلی برای محاسبه چارت */
        val mainPlanets = listOf(SUN, MOON, MERCURY, VENUS, MARS, JUPITER, SATURN, URANUS, NEPTUNE, PLUTO)
    }
}
