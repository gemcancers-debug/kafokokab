# PROJECT_STATE.md

## Current Phase
Phase 3 - Astrology Core + Profile Persistence + Build fix

## مرحله فعلی
فاز ۳ - موتور نجوم + ذخیره پروفایل + رفع خطای بیلد

---

## Completed
- Full Onboarding UI + Home Dashboard
- UserProfile + OnboardingViewModel + DataStore persistence
- Firebase foundation + Auth layer + correct google-services.json
- Astrology domain models + Calculator + Stub
- BirthChartViewModel + BirthChartScreen
- HomeViewModel (real user name)
- **Fixed: Removed @Inject from domain UseCase (Clean Architecture)**
- **Added @Provides for CalculateBirthChartUseCase in AstrologyModule**
- **Fixed: Added Hilt plugin + dependencies to core-data module (Unresolved reference 'dagger')**

## انجام شده
- UI کامل آنبوردینگ + داشبورد
- ذخیره پایدار پروفایل
- صفحه چارت + نام واقعی کاربر
- google-services.json صحیح
- **رفع خطای کامپایل UseCase در لایه Domain**
- **رفع خطای Unresolved reference dagger با اضافه کردن Hilt به core-data**

---

## In Progress
- Waiting for successful build + APK artifact + user test of Google Sign-In

## در حال انجام
- منتظر بیلد موفق، دانلود APK و تست ورود با جیمیل

---

## Blocked / Attention Needed
- Real ephemeris still Stub
- Gradle Wrapper (gradlew) is missing in repo (workflow falls back to system Gradle – works but not ideal)

## نیاز به توجه
- محاسبات واقعی نجومی هنوز Stub است
- فایل gradlew در ریپازیتوری وجود ندارد (فعلاً با system Gradle کار می‌کند)

---

## Next Step
1. Wait for green build + APK artifact from GitHub Actions
2. User tests Google Sign-In with the new SHA-1
3. If green → continue feature development (or polish Home / Chart)

## مرحله بعد
۱. بیلد سبز و دانلود APK
۲. تست ورود با جیمیل توسط کاربر
۳. ادامه توسعه

---

**Last Updated:** 2026-08-11  
**Updated By:** AI Principal Engineer (Grok)
