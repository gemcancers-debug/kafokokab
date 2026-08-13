/*
نام فایل: IranCities.kt
مسیر: core/core-domain/.../geo/
وظیفه: مختصات جغرافیایی واقعی شهرهای ایران برای محاسبه طلوع
نویسنده: AI Principal Engineer
تاریخ: 2026-08-13

مختصات از منابع عمومی جغرافیایی (تقریباً مرکز شهر).
اگر شهر پیدا نشد، تهران به‌عنوان پیش‌فرض برگردانده می‌شود.
*/

package com.kafokokab.core.domain.geo

/**
 * مختصات جغرافیایی یک شهر.
 */
data class GeoLocation(
    val name: String,
    val latitude: Double,
    val longitude: Double
)

/**
 * پایگاه کوچک مختصات واقعی شهرهای ایران.
 */
object IranCities {

    private val cities: List<GeoLocation> = listOf(
        GeoLocation("تهران", 35.6892, 51.3890),
        GeoLocation("مشهد", 36.2970, 59.6062),
        GeoLocation("اصفهان", 32.6546, 51.6680),
        GeoLocation("شیراز", 29.5918, 52.5837),
        GeoLocation("تبریز", 38.0962, 46.2738),
        GeoLocation("کرج", 35.8400, 50.9391),
        GeoLocation("اهواز", 31.3183, 48.6706),
        GeoLocation("قم", 34.6416, 50.8746),
        GeoLocation("کرمانشاه", 34.3142, 47.0650),
        GeoLocation("ارومیه", 37.5527, 45.0761),
        GeoLocation("رشت", 37.2808, 49.5832),
        GeoLocation("زاهدان", 29.4963, 60.8629),
        GeoLocation("همدان", 34.7983, 48.5148),
        GeoLocation("کرمان", 30.2839, 57.0834),
        GeoLocation("یزد", 31.8974, 54.3569),
        GeoLocation("اردبیل", 38.2498, 48.2933),
        GeoLocation("بندرعباس", 27.1832, 56.2666),
        GeoLocation("اراک", 34.0917, 49.6892),
        GeoLocation("اسلامشهر", 35.5446, 51.2305),
        GeoLocation("زنجان", 36.6736, 48.4787),
        GeoLocation("سنندج", 35.3219, 46.9862),
        GeoLocation("قزوین", 36.2797, 50.0049),
        GeoLocation("خرم‌آباد", 33.4878, 48.3558),
        GeoLocation("گرگان", 36.8456, 54.4393),
        GeoLocation("ساری", 36.5633, 53.0601),
        GeoLocation("بوشهر", 28.9234, 50.8203),
        GeoLocation("بیرجند", 32.8663, 59.2211),
        GeoLocation("ایلام", 33.6374, 46.4226),
        GeoLocation("یاسوج", 30.6684, 51.5879),
        GeoLocation("شهرکرد", 32.3256, 50.8644),
        GeoLocation("بجنورد", 37.4750, 57.3333),
        GeoLocation("سمنان", 35.5769, 53.3920),
        GeoLocation("کاشان", 33.9850, 51.4100),
        GeoLocation("نجف‌آباد", 32.6342, 51.3667),
        GeoLocation("سبزوار", 36.2152, 57.6791),
        GeoLocation("آمل", 36.4697, 52.3508),
        GeoLocation("بابل", 36.5513, 52.6789),
        GeoLocation("خوی", 38.5503, 44.9521),
        GeoLocation("مراغه", 37.3890, 46.2378),
        GeoLocation("ساوه", 35.0213, 50.3566)
    )

    val defaultTehran: GeoLocation = cities.first { it.name == "تهران" }

    /**
     * پیدا کردن مختصات بر اساس نام شهر (تطبیق نرم).
     */
    fun resolve(cityName: String?, provinceName: String? = null): GeoLocation {
        val query = (cityName ?: "").trim()
        if (query.isBlank()) return defaultTehran

        cities.firstOrNull { it.name == query }?.let { return it }
        cities.firstOrNull { it.name.contains(query) || query.contains(it.name) }?.let { return it }

        val province = (provinceName ?: "").trim()
        if (province.isNotBlank()) {
            cities.firstOrNull { it.name.contains(province.take(3)) }?.let { return it }
        }

        return defaultTehran
    }
}
