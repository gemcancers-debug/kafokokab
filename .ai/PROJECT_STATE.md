# PROJECT_STATE.md

## Current Phase
Phase 3 - Astrology Core Engine + Profile Persistence + Home personalization

## مرحله فعلی
فاز ۳ - موتور نجوم + ذخیره پروفایل + شخصی‌سازی صفحه اصلی

---

## Completed
- Full Onboarding UI + Home Dashboard
- UserProfile + OnboardingViewModel + DataStore persistence
- Firebase foundation + Auth layer
- Astrology domain models + Calculator + Stub
- BirthChartViewModel + BirthChartScreen
- **HomeViewModel** (reads real user name from ProfileRepository)
- HomeScreen now shows real user name and birth summary when available

## انجام شده
- UI کامل آنبوردینگ + داشبورد
- ذخیره پایدار پروفایل
- صفحه چارت
- **نمایش نام واقعی کاربر در صفحه اصلی**
- نمایش خلاصه تاریخ تولد در کارت چارت (اگر موجود باشد)

---

## In Progress
- Google Sign-In SHA-1 (user will handle later)

## در حال انجام
- SHA-1 هنوز نیاز به تنظیم دارد

---

## Blocked / Attention Needed
- Google Sign-In: SHA-1 missing
- Real ephemeris not yet integrated (Stub)
- City → coordinates mapping not implemented

## نیاز به توجه
- SHA-1 برای ورود با گوگل
- محاسبات واقعی نجومی هنوز Stub است

---

## Next Step
1. Improve BirthChartScreen to clearly show sample vs real data
2. Add simple daily horoscope placeholder
3. Later: real offline ephemeris

## مرحله بعد
۱. بهبود صفحه چارت برای تمایز داده نمونه و واقعی
۲. اضافه کردن فال روزانه ساده
۳. بعداً: محاسبه واقعی آفلاین

---

**Last Updated:** 2026-08-10  
**Updated By:** AI Principal Engineer (Grok)
