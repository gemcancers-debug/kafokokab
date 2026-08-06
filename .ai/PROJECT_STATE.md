# PROJECT_STATE.md

## Current Phase
Phase 3 - Astrology Core Engine (Foundation)

## مرحله فعلی
فاز ۳ - موتور اصلی نجوم (پایه و مدل‌های دامنه)

---

## Completed
- Full Onboarding UI + Home Dashboard
- UserProfile + OnboardingViewModel (fully bound)
- Firebase foundation + Auth layer
- LoginScreen with improved error handling
- GlassCard + PremiumBlurBox
- All Modifier import fixes + successful CI builds
- **Domain models for Astrology (2026-08-06)**
  - Planet (enum + فارسی)
  - ZodiacSign + Element + Modality
  - PlanetPosition
  - BirthChart + ChartSystem

## انجام شده
- UI کامل آنبوردینگ + داشبورد
- مدل و ViewModel آنبوردینگ (اتصال کامل)
- پایه Firebase و احراز هویت
- **مدل‌های دامنه نجوم (سیاره، برج، موقعیت، چارت)**

---

## In Progress
- Waiting for user to finish Firebase Google Sign-In setup (Web Client ID)
- Preparing calculation engine structure

## در حال انجام
- منتظر اتمام تنظیم Firebase توسط کاربر
- آماده‌سازی ساختار موتور محاسبه

---

## Blocked / Attention Needed
- `default_web_client_id` still placeholder
- User must:
  1. Enable Google Sign-In in Firebase Authentication
  2. Re-download google-services.json
  3. Put client_type:3 client_id into strings.xml
  4. Rebuild & test login

## نیاز به توجه
- Web Client ID هنوز placeholder است (کاربر در حال تنظیم)

---

## Next Step
1. User finishes Firebase setup → full login test
2. Create AstrologyCalculator interface + simple stub implementation
3. Later: real ephemeris data or Swiss Ephemeris integration (offline)

## مرحله بعد
۱. اتمام تنظیم Firebase و تست ورود
۲. ایجاد رابط AstrologyCalculator + پیاده‌سازی اولیه
۳. بعداً: داده اپیمر یا Swiss Ephemeris آفلاین

---

**Last Updated:** 2026-08-06  
**Updated By:** AI Principal Engineer (Grok)
