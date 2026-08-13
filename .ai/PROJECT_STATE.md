# PROJECT_STATE.md

## Current Phase
Phase 3 - Real offline astrology engine (no mock)

## مرحله فعلی
فاز ۳ - موتور نجوم واقعی آفلاین (بدون Mock)

---

## Completed
- Full Onboarding UI + Home Dashboard + Daily Insight
- UserProfile + DataStore persistence
- Firebase Auth foundation (Google Sign-In deferred – no keystore for now)
- BirthChart UI + interpretation cards
- **FormulaAstrologyCalculator** – real offline planetary formulas (Meeus-style)
- **PersianDateConverter** – Jalali → Gregorian for Iranian birth dates
- **IranCities** – real lat/lon for major Iranian cities
- **Removed StubAstrologyCalculator and sample birth data**
- BirthChartViewModel uses only real profile data

## انجام شده
- UI آنبوردینگ + داشبورد + طالع امروز + تفسیر چارت
- **موتور محاسبه واقعی آفلاین جایگزین Stub شد**
- **تبدیل تاریخ شمسی و مختصات واقعی شهرها**
- **حذف کامل داده نمونه / Mock از چارت**

---

## In Progress
- Green build after real engine push
- User test with real birth date from onboarding

## در حال انجام
- بیلد و تست با تاریخ تولد واقعی کاربر

---

## Blocked / Attention Needed
- Google Sign-In / keystore deferred by user request
- Gradle Wrapper still missing
- Formula accuracy is educational, not Swiss Ephemeris grade

## نیاز به توجه
- ورود گوگل فعلاً کنار گذاشته شده
- دقت موتور آموزشی است

---

## Next Step
1. Build APK and test chart with real onboarding birth data
2. Optional: improve city list / house cusps
3. Later: stable keystore when ready

## مرحله بعد
۱. بیلد و تست چارت با تاریخ واقعی
۲. بهبود لیست شهرها در صورت نیاز
۳. بعداً keystore وقتی آماده بود

---

**Last Updated:** 2026-08-13  
**Updated By:** AI Principal Engineer (Grok)  
**Note:** Keystore deferred; no mock planetary data.
