# PROJECT_STATE.md

## Current Phase
Phase 3 - Astrology Core Engine (Calculator Interface + Stub)

## مرحله فعلی
فاز ۳ - موتور نجوم (رابط محاسبه + پیاده‌سازی موقت)

---

## Completed
- Full Onboarding UI + Home Dashboard
- UserProfile + OnboardingViewModel (fully bound)
- Firebase foundation + Auth layer
- LoginScreen with improved error handling
- GlassCard + PremiumBlurBox
- Astrology domain models (Planet, ZodiacSign, PlanetPosition, BirthChart)
- Real default_web_client_id set
- **AstrologyCalculator interface**
- **CalculateBirthChartUseCase**
- **StubAstrologyCalculator** (temporary implementation for UI development)
- **AstrologyModule** (Hilt binding)

## انجام شده
- UI کامل آنبوردینگ + داشبورد
- مدل و ViewModel آنبوردینگ
- پایه Firebase و احراز هویت
- مدل‌های دامنه نجوم
- **رابط و پیاده‌سازی موقت موتور محاسبه چارت**

---

## In Progress
- Google Sign-In still needs correct SHA-1 (user will handle later)
- Preparing to connect calculator to Home / Chart screen

## در حال انجام
- SHA-1 هنوز نیاز به تنظیم دارد (کاربر بعداً انجام می‌دهد)
- آماده‌سازی اتصال محاسبه به صفحه چارت

---

## Blocked / Attention Needed
- Google Sign-In: SHA-1 fingerprint for package `com.kafokokab.app` still missing
- Real ephemeris data not yet integrated (currently using Stub)

## نیاز به توجه
- SHA-1 برای ورود با گوگل هنوز تنظیم نشده
- محاسبات واقعی نجومی هنوز Stub است

---

## Next Step
1. Create a simple BirthChartViewModel + basic Chart screen UI
2. Or improve HomeScreen to show sun/moon sign from calculator
3. Later: replace Stub with real offline calculation

## مرحله بعد
۱. ساخت ViewModel و صفحه ساده نمایش چارت
۲. یا نمایش برج خورشید/ماه در صفحه اصلی
۳. بعداً: جایگزینی Stub با محاسبه واقعی آفلاین

---

**Last Updated:** 2026-08-08  
**Updated By:** AI Principal Engineer (Grok)
