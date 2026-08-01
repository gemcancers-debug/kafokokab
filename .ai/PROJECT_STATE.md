# PROJECT_STATE.md

## Current Phase
Phase 2 - Onboarding UI complete + basic data layer started

## مرحله فعلی
فاز ۲ - UI آنبوردینگ کامل + شروع لایه داده

---

## Completed
- Full Onboarding UI (5 screens)
- Home Dashboard matching design
- UserProfile domain model (core-domain)
- OnboardingViewModel with StateFlow
- Clean separation: Domain model has no Android dependencies

## انجام شده
- UI کامل آنبوردینگ (۵ صفحه)
- داشبورد خانه مطابق طراحی
- مدل دامنه UserProfile
- OnboardingViewModel با StateFlow
- جداسازی تمیز: مدل دامنه وابستگی Android ندارد

---

## In Progress
- Binding ViewModel to the actual screens (next small step)

## در حال انجام
- اتصال ViewModel به صفحات واقعی آنبوردینگ

---

## Blocked
- Real Google Sign-In → needs Firebase project + google-services.json from user

## مسدود شده
- ورود واقعی با گوگل → نیاز به پروژه Firebase و فایل google-services.json از طرف کاربر

---

## Not Done
- Connect ViewModel to BirthInfo / PersonalInfo / ExtraInfo / Review screens
- Persist profile with DataStore
- Real Google Sign-In
- Phase 3 Astrology Engine

## انجام نشده
- اتصال ViewModel به صفحات
- ذخیره پایدار با DataStore
- ورود واقعی گوگل
- فاز ۳ موتور آسترولوژی

---

## Next Step
Connect OnboardingViewModel to the existing screens (one screen at a time)
OR ask user for Firebase setup to start Google Sign-In

## مرحله بعد
اتصال OnboardingViewModel به صفحات موجود (صفحه به صفحه)
یا درخواست راه‌اندازی Firebase از کاربر برای شروع Google Sign-In

---

## Architecture Notes
- UserProfile lives in core-domain (pure Kotlin)
- OnboardingViewModel is in app module and uses Hilt
- Ready for Repository + DataStore later

---

**Last Updated:** 2026-08-01  
**Updated By:** AI Principal Engineer (Grok)
