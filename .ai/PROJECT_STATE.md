# PROJECT_STATE.md

## Current Phase
Phase 3 - Astrology Core + Daily Insight on Home

## مرحله فعلی
فاز ۳ - موتور نجوم + طالع نمادین روزانه روی Home

---

## Completed
- Full Onboarding UI + Home Dashboard
- UserProfile + OnboardingViewModel + DataStore persistence
- Firebase foundation + Auth layer
- Astrology domain models + Calculator + Stub
- BirthChartViewModel + BirthChartScreen
- HomeViewModel (real user name)
- Clean Architecture Hilt fixes
- **MainActivity restored** (was corrupted by wrong project code)
- **DailyInsightCard on Home** – symbolic daily message (educational tone, no hard predictions)

## انجام شده
- UI کامل آنبوردینگ + داشبورد
- ذخیره پایدار پروفایل
- صفحه چارت
- MainActivity بازگردانی شد
- **کارت طالع امروز روی صفحه اصلی اضافه شد**

---

## In Progress
- Waiting for green build after Daily Insight push
- User test of Home + Daily Insight card

## در حال انجام
- منتظر بیلد سبز پس از push طالع امروز
- تست کارت طالع امروز توسط کاربر

---

## Blocked / Attention Needed
- Real ephemeris still Stub
- Gradle Wrapper (gradlew) is missing in repo
- Google Sign-In SHA-1 still fragile on CI builds (needs stable debug keystore)

## نیاز به توجه
- محاسبات واقعی نجومی هنوز Stub است
- فایل gradlew وجود ندارد
- ورود با گوگل روی بیلدهای CI هنوز شکننده است

---

## Next Step
1. Green build + APK from this commit
2. User tests Daily Insight card on Home
3. Next feature options:
   - Short interpretation texts on BirthChart (Sun/Moon/Rising)
   - Stable debug keystore for Google Sign-In
   - Premium blur polish on locked tools

## مرحله بعد
۱. بیلد سبز و دانلود APK
۲. تست کارت طالع امروز
۳. گزینه‌های بعدی: تفسیر کوتاه چارت / keystore ثابت / پالیش Premium

---

**Last Updated:** 2026-08-13  
**Updated By:** AI Principal Engineer (Grok) – KafoKokab Skill active
