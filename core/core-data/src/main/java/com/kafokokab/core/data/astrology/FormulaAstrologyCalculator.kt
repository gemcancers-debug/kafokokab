/*
نام فایل: FormulaAstrologyCalculator.kt
مسیر: core/core-data/.../astrology/
وظیفه: محاسبه واقعی موقعیت سیارات با فرمول‌های نجومی آفلاین
نویسنده: AI Principal Engineer
تاریخ: 2026-08-13

منبع الگوریتم‌ها:
- Jean Meeus, Astronomical Algorithms (ساده‌سازی‌شده برای موبایل)
- Julian Day، طول دایره‌البروجی خورشید/ماه/سیارات
- Local Sidereal Time برای طلوع (Ascendant)

کاملاً آفلاین؛ بدون داده Mock؛ دقت آموزشی/کاربردی
*/

package com.kafokokab.core.data.astrology

import com.kafokokab.core.domain.model.astrology.BirthChart
import com.kafokokab.core.domain.model.astrology.ChartSystem
import com.kafokokab.core.domain.model.astrology.Planet
import com.kafokokab.core.domain.model.astrology.PlanetPosition
import com.kafokokab.core.domain.repository.AstrologyCalculator
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin
import kotlin.math.tan

@Singleton
class FormulaAstrologyCalculator @Inject constructor() : AstrologyCalculator {

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
        val greg = PersianDateConverter.toGregorianIfNeeded(year, month, day)

        val jd = julianDay(
            greg.year, greg.month, greg.day,
            hour.coerceIn(0, 23),
            minute.coerceIn(0, 59)
        )
        val t = (jd - 2451545.0) / 36525.0

        val sunLon = sunLongitude(t)
        val moonLon = moonLongitude(t)
        val mercuryLon = planetLongitude(t, PlanetOrbital.MERCURY)
        val venusLon = planetLongitude(t, PlanetOrbital.VENUS)
        val marsLon = planetLongitude(t, PlanetOrbital.MARS)
        val jupiterLon = planetLongitude(t, PlanetOrbital.JUPITER)
        val saturnLon = planetLongitude(t, PlanetOrbital.SATURN)
        val uranusLon = planetLongitude(t, PlanetOrbital.URANUS)
        val neptuneLon = planetLongitude(t, PlanetOrbital.NEPTUNE)
        val plutoLon = planetLongitude(t, PlanetOrbital.PLUTO)

        val lstHours = localSiderealTime(jd, longitude)
        val ascLon = ascendantLongitude(lstHours, latitude)

        val ayanamsa = if (system == ChartSystem.VEDIC) lahiriAyanamsa(t) else 0.0

        fun tropicalToSystem(lon: Double): Double = normalize(lon - ayanamsa)

        val positions = listOf(
            pos(Planet.SUN, tropicalToSystem(sunLon), sunSpeed(t)),
            pos(Planet.MOON, tropicalToSystem(moonLon), 13.176),
            pos(Planet.MERCURY, tropicalToSystem(mercuryLon), 1.2, isRetro(t, PlanetOrbital.MERCURY)),
            pos(Planet.VENUS, tropicalToSystem(venusLon), 0.8, isRetro(t, PlanetOrbital.VENUS)),
            pos(Planet.MARS, tropicalToSystem(marsLon), 0.5, isRetro(t, PlanetOrbital.MARS)),
            pos(Planet.JUPITER, tropicalToSystem(jupiterLon), 0.08, isRetro(t, PlanetOrbital.JUPITER)),
            pos(Planet.SATURN, tropicalToSystem(saturnLon), 0.03, isRetro(t, PlanetOrbital.SATURN)),
            pos(Planet.URANUS, tropicalToSystem(uranusLon), 0.01),
            pos(Planet.NEPTUNE, tropicalToSystem(neptuneLon), 0.006),
            pos(Planet.PLUTO, tropicalToSystem(plutoLon), 0.004),
            pos(Planet.ASCENDANT, tropicalToSystem(ascLon), 0.0),
            pos(Planet.MIDHEAVEN, tropicalToSystem(normalize(ascLon + 90.0)), 0.0)
        )

        return BirthChart(
            positions = positions,
            system = system,
            ayanamsa = if (system == ChartSystem.VEDIC) ayanamsa else null
        )
    }

    private fun julianDay(year: Int, month: Int, day: Int, hour: Int, minute: Int): Double {
        var y = year
        var m = month
        if (m <= 2) {
            y -= 1
            m += 12
        }
        val a = floor(y / 100.0)
        val b = 2 - a + floor(a / 4.0)
        val dayFraction = (hour + minute / 60.0) / 24.0
        return floor(365.25 * (y + 4716)) +
            floor(30.6001 * (m + 1)) +
            day + dayFraction + b - 1524.5
    }

    private fun sunLongitude(t: Double): Double {
        val l0 = normalize(280.46646 + 36000.76983 * t + 0.0003032 * t * t)
        val m = Math.toRadians(normalize(357.52911 + 35999.05029 * t - 0.0001537 * t * t))
        val c = (1.914602 - 0.004817 * t - 0.000014 * t * t) * sin(m) +
            (0.019993 - 0.000101 * t) * sin(2 * m) +
            0.000289 * sin(3 * m)
        return normalize(l0 + c)
    }

    private fun sunSpeed(t: Double): Double {
        val m = Math.toRadians(normalize(357.52911 + 35999.05029 * t))
        return 0.9856 + 0.0167 * cos(m)
    }

    private fun moonLongitude(t: Double): Double {
        val lp = normalize(218.3164477 + 481267.88123421 * t)
        val d = Math.toRadians(normalize(297.8501921 + 445267.1114034 * t))
        val m = Math.toRadians(normalize(357.5291092 + 35999.0502909 * t))
        val mp = Math.toRadians(normalize(134.9633964 + 477198.8675055 * t))
        val f = Math.toRadians(normalize(93.2720950 + 483202.0175233 * t))

        val lon = lp +
            6.288774 * sin(mp) +
            1.274027 * sin(2 * d - mp) +
            0.658314 * sin(2 * d) +
            0.213618 * sin(2 * mp) -
            0.185116 * sin(m) -
            0.114332 * sin(2 * f) +
            0.058793 * sin(2 * d - 2 * mp) +
            0.057066 * sin(2 * d - m - mp) +
            0.053322 * sin(2 * d + mp) +
            0.045758 * sin(2 * d - m)

        return normalize(lon)
    }

    private data class PlanetOrbital(
        val L0: Double, val L1: Double,
        val a: Double,
        val e0: Double, val e1: Double,
        val peri0: Double, val peri1: Double
    ) {
        companion object {
            val MERCURY = PlanetOrbital(252.2509, 149472.6746, 0.387, 0.2056, 0.0000, 77.4561, 1.556)
            val VENUS = PlanetOrbital(181.9798, 58517.8156, 0.723, 0.0167, 0.0000, 131.6025, 1.402)
            val MARS = PlanetOrbital(355.4330, 19140.3023, 1.524, 0.0934, 0.0001, 336.0491, 1.851)
            val JUPITER = PlanetOrbital(34.3515, 3034.9057, 5.203, 0.0485, -0.0001, 14.3313, 1.613)
            val SATURN = PlanetOrbital(50.0775, 1222.1138, 9.537, 0.0555, -0.0003, 93.0572, 1.964)
            val URANUS = PlanetOrbital(314.0550, 428.4660, 19.19, 0.0463, -0.0001, 173.0053, 1.486)
            val NEPTUNE = PlanetOrbital(304.3487, 218.4862, 30.07, 0.0095, 0.0000, 48.1200, 1.426)
            val PLUTO = PlanetOrbital(238.9580, 145.2080, 39.48, 0.2488, 0.0000, 224.0689, 1.396)
        }
    }

    private fun planetLongitude(t: Double, orb: PlanetOrbital): Double {
        val L = normalize(orb.L0 + orb.L1 * t)
        val e = orb.e0 + orb.e1 * t
        val peri = normalize(orb.peri0 + orb.peri1 * t)
        val M = Math.toRadians(normalize(L - peri))
        val C = (2 * e - e * e * e / 4) * sin(M) +
            1.25 * e * e * sin(2 * M) +
            1.083 * e * e * e * sin(3 * M)
        return normalize(L + Math.toDegrees(C))
    }

    private fun isRetro(t: Double, orb: PlanetOrbital): Boolean {
        val now = planetLongitude(t, orb)
        val later = planetLongitude(t + 1.0 / 36525.0, orb)
        val delta = normalize(later - now + 180.0) - 180.0
        return delta < 0
    }

    private fun localSiderealTime(jd: Double, longitudeEast: Double): Double {
        val t = (jd - 2451545.0) / 36525.0
        var gmst = 280.46061837 + 360.98564736629 * (jd - 2451545.0) +
            0.000387933 * t * t - t * t * t / 38710000.0
        gmst = normalize(gmst)
        val lst = normalize(gmst + longitudeEast)
        return lst / 15.0
    }

    private fun ascendantLongitude(lstHours: Double, latitude: Double): Double {
        val ramc = Math.toRadians(lstHours * 15.0)
        val obliquity = Math.toRadians(23.439291)
        val lat = Math.toRadians(latitude)
        val y = -cos(ramc)
        val x = sin(obliquity) * tan(lat) + cos(obliquity) * sin(ramc)
        return normalize(Math.toDegrees(atan2(y, x)))
    }

    private fun lahiriAyanamsa(t: Double): Double = 23.85 + 1.396971 * t

    private fun normalize(deg: Double): Double {
        var d = deg % 360.0
        if (d < 0) d += 360.0
        return d
    }

    private fun pos(
        planet: Planet,
        longitude: Double,
        speed: Double,
        retrograde: Boolean = false
    ) = PlanetPosition(
        planet = planet,
        longitude = normalize(longitude),
        speed = speed,
        isRetrograde = retrograde
    )
}
