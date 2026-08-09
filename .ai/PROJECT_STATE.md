# PROJECT_STATE.md

## Current Phase
Phase 3 - Astrology Core Engine + Basic Chart UI

## مرحله فعلی
فاز ۳ - موتور نجوم + صفحه نمایش چارت پایه

---

## Completed
- Full Onboarding UI + Home Dashboard
- UserProfile + OnboardingViewModel (fully bound)
- Firebase foundation + Auth layer
- Astrology domain models (Planet, ZodiacSign, PlanetPosition, BirthChart)
- AstrologyCalculator interface + CalculateBirthChartUseCase
- StubAstrologyCalculator (temporary)
- AstrologyModule (Hilt)
- **BirthChartViewModel**
- **BirthChartScreen** (خلاصه خورشید/ماه/طلوع + لیست سیارات)
- Navigation to Chart screen from Home
- System switcher (Western / Vedic)

## انجام شده
- UI کامل آنبوردینگ + داشبورد
- مدل و ViewModel آنبوردینگ
- پایه Firebase و احراز هویت
- مدل‌های دامنه نجوم
- رابط و پیاده‌سازی موقت موتور محاسبه
- **صفحه نمایش چارت تولد + ViewModel**
- اتصال دکمه «به‌دست آوردن هوروسکوپ» به صفحه چارت

---

## In Progress
- Google Sign-In still needs correct SHA-1 (user will handle later)

## در حال انجام
- SHA-1 هنوز نیاز به تنظیم دارد (کاربر بعداً انجام می‌دهد)

---

## Blocked / Attention Needed
- Google Sign-In: SHA-1 fingerprint for package `com.kafokokab.app` still missing
- Real ephemeris data not yet integrated (currently using Stub)
- User birth data not yet persisted (chart uses sample date)

## نیاز به توجه
- SHA-1 برای ورود با گوگل هنوز تنظیم نشده
- محاسبات واقعی نجومی هنوز Stub است
- داده تولد کاربر هنوز ذخیره نمی‌شود (چارت از تاریخ نمونه استفاده می‌کند)

---

## Next Step
1. Persist UserProfile (DataStore or Room)
2. Feed real birth data from onboarding into BirthChartViewModel
3. Later: replace Stub with real offline calculation

## مرحله بعد
۱. ذخیره پایدار UserProfile
۲. اتصال داده واقعی تولد به ViewModel چارت
۳. بعداً: جایگزینی Stub با محاسبه واقعی آفلاین

---

**Last Updated:** 2026-08-08  
**Updated By:** AI Principal Engineer (Grok)
