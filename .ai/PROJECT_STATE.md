# PROJECT_STATE.md

## Current Phase
Phase 3 - Astrology Core Engine + Profile Persistence

## مرحله فعلی
فاز ۳ - موتور نجوم + ذخیره پایدار پروفایل

---

## Completed
- Full Onboarding UI + Home Dashboard
- UserProfile + OnboardingViewModel
- Firebase foundation + Auth layer
- Astrology domain models + Calculator + Stub
- BirthChartViewModel + BirthChartScreen
- Navigation to Chart from Home
- **ProfileRepository (DataStore)**
- **ProfileRepositoryImpl**
- **ProfileModule (Hilt)**
- OnboardingViewModel now saves profile on complete
- BirthChartViewModel prioritizes real birth data if available

## انجام شده
- UI کامل آنبوردینگ + داشبورد
- مدل و ViewModel آنبوردینگ
- پایه Firebase و احراز هویت
- مدل‌های دامنه + موتور محاسبه موقت
- صفحه نمایش چارت
- **ذخیره پایدار پروفایل با DataStore**
- اتصال ذخیره به اتمام آنبوردینگ
- اولویت چارت با داده واقعی کاربر

---

## In Progress
- Google Sign-In SHA-1 (user will handle later)

## در حال انجام
- SHA-1 هنوز نیاز به تنظیم دارد

---

## Blocked / Attention Needed
- Google Sign-In: SHA-1 missing
- Real ephemeris not yet integrated (Stub)
- City → coordinates mapping not implemented yet (still uses Tehran default)

## نیاز به توجه
- SHA-1 برای ورود با گوگل
- محاسبات واقعی نجومی هنوز Stub است
- تبدیل شهر به مختصات جغرافیایی هنوز پیاده نشده

---

## Next Step
1. Show real user name + birth summary on Home
2. Improve BirthChartScreen to indicate sample vs real data
3. Later: real offline ephemeris / Swiss Ephemeris

## مرحله بعد
۱. نمایش نام و خلاصه تولد واقعی در صفحه اصلی
۲. بهبود صفحه چارت برای تمایز داده نمونه و واقعی
۳. بعداً: محاسبه واقعی آفلاین

---

**Last Updated:** 2026-08-09  
**Updated By:** AI Principal Engineer (Grok)
