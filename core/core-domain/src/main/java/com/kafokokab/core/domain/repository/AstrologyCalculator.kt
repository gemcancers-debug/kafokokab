/*
نام فایل: AstrologyCalculator.kt
مسیر: core/core-domain/.../repository/
وظیفه: رابط (Interface) محاسبه چارت تولد
نویسنده: AI Principal Engineer
تاریخ: 2026-08-08

این رابط لایه Domain است.
پیاده‌سازی واقعی (با اپیمر یا Swiss Ephemeris) در لایه Data انجام می‌شود.
فعلاً یک Stub ساده داریم تا UI بتواند کار کند.
*/

package com.kafokokab.core.domain.repository

import com.kafokokab.core.domain.model.astrology.BirthChart
import com.kafokokab.core.domain.model.astrology.ChartSystem

/**
 * رابط محاسبه چارت نجومی.
 *
 * هر پیاده‌سازی باید بتواند بر اساس تاریخ، ساعت و مختصات جغرافیایی
 * موقعیت سیارات را محاسبه کند.
 */
interface AstrologyCalculator {

    /**
     * محاسبه چارت تولد.
     *
     * @param year سال میلادی
     * @param month ماه (۱ تا ۱۲)
     * @param day روز
     * @param hour ساعت (۰ تا ۲۳)
     * @param minute دقیقه
     * @param latitude عرض جغرافیایی
     * @param longitude طول جغرافیایی
     * @param system سیستم محاسبه (غربی یا ودیک)
     * @return چارت کامل
     */
    suspend fun calculateBirthChart(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        latitude: Double,
        longitude: Double,
        system: ChartSystem = ChartSystem.WESTERN
    ): BirthChart
}
