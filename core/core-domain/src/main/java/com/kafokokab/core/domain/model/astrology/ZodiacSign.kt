/*
نام فایل: ZodiacSign.kt
مسیر: core/core-domain/.../model/astrology/
وظیفه: تعریف ۱۲ برج فلکی (دایره‌البروج)
نویسنده: AI Principal Engineer
تاریخ: 2026-08-06

نکته:
- عنصر (Element) و کیفیت (Modality) برای تحلیل‌های بعدی لازم است
- نام فارسی برای نمایش در اپ استفاده می‌شود
*/

package com.kafokokab.core.domain.model.astrology

/**
 * ۱۲ برج دایره‌البروج.
 */
enum class ZodiacSign(
    val englishName: String,
    val persianName: String,
    val symbol: String,
    val element: Element,
    val modality: Modality,
    val startDegree: Int   // درجه شروع در دایره‌البروج (۰ تا ۳۳۰)
) {
    ARIES("Aries", "حمل", "♈", Element.FIRE, Modality.CARDINAL, 0),
    TAURUS("Taurus", "ثور", "♉", Element.EARTH, Modality.FIXED, 30),
    GEMINI("Gemini", "جوزا", "♊", Element.AIR, Modality.MUTABLE, 60),
    CANCER("Cancer", "سرطان", "♋", Element.WATER, Modality.CARDINAL, 90),
    LEO("Leo", "اسد", "♌", Element.FIRE, Modality.FIXED, 120),
    VIRGO("Virgo", "سنبله", "♍", Element.EARTH, Modality.MUTABLE, 150),
    LIBRA("Libra", "میزان", "♎", Element.AIR, Modality.CARDINAL, 180),
    SCORPIO("Scorpio", "عقرب", "♏", Element.WATER, Modality.FIXED, 210),
    SAGITTARIUS("Sagittarius", "قوس", "♐", Element.FIRE, Modality.MUTABLE, 240),
    CAPRICORN("Capricorn", "جدی", "♑", Element.EARTH, Modality.CARDINAL, 270),
    AQUARIUS("Aquarius", "دلو", "♒", Element.AIR, Modality.FIXED, 300),
    PISCES("Pisces", "حوت", "♓", Element.WATER, Modality.MUTABLE, 330);

    companion object {
        /** پیدا کردن برج از روی درجه مطلق (۰ تا ۳۶۰) */
        fun fromDegree(degree: Double): ZodiacSign {
            val normalized = ((degree % 360) + 360) % 360
            return entries.first { normalized >= it.startDegree && normalized < it.startDegree + 30 }
        }
    }
}

/** عناصر چهارگانه */
enum class Element(val persianName: String) {
    FIRE("آتش"),
    EARTH("خاک"),
    AIR("هوا"),
    WATER("آب")
}

/** کیفیت‌های سه‌گانه */
enum class Modality(val persianName: String) {
    CARDINAL("مقدم"),
    FIXED("ثابت"),
    MUTABLE("ذوحالتین")
}
