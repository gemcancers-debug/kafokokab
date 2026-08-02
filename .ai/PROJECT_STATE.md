# PROJECT_STATE.md

## Current Phase
Phase 2 - Firebase foundation added

## مرحله فعلی
فاز ۲ - پایه Firebase اضافه شد

---

## Completed
- Full Onboarding UI + Home Dashboard
- UserProfile + OnboardingViewModel
- google-services.json added (contains com.kafokokab.app)
- Google Services plugin + Firebase Auth + Play Services Auth dependencies

## انجام شده
- UI کامل آنبوردینگ + داشبورد
- UserProfile + OnboardingViewModel
- فایل google-services.json اضافه شد
- پلاگین Google Services + وابستگی‌های Firebase Auth

---

## In Progress
- Waiting for user to finish Firebase setup (SHA-1 + enable Google provider)

## در حال انجام
- منتظر تکمیل تنظیمات Firebase توسط کاربر (SHA-1 + فعال‌سازی Google)

---

## Blocked
- Real Google Sign-In button logic
  Reason: oauth_client is empty in google-services.json
  User must:
  1. Enable Google Sign-in method in Firebase Authentication
  2. Add SHA-1 fingerprint of the debug keystore
  3. Re-download google-services.json if needed

## مسدود شده
- منطق واقعی دکمه ورود با گوگل
  دلیل: oauth_client در فایل خالی است
  کاربر باید:
  ۱. روش ورود Google را در Firebase Authentication فعال کند
  ۲. اثر انگشت SHA-1 را اضافه کند
  ۳. در صورت نیاز فایل را دوباره دانلود کند

---

## Not Done
- Bind ViewModel to screens
- Actual Google Sign-In implementation code
- DataStore persistence
- Phase 3 Astrology Engine

## انجام نشده
- اتصال ViewModel به صفحات
- پیاده‌سازی واقعی Google Sign-In
- ذخیره با DataStore
- فاز ۳ موتور آسترولوژی

---

## Next Step
User action required:
1. Firebase Console → Authentication → Sign-in method → Enable Google
2. Project Settings → Add SHA-1 (I can give the Termux command)
3. Tell me when done so I can implement the Sign-In code

## مرحله بعد
اقدام لازم از سمت کاربر:
۱. فعال کردن Google در Authentication
۲. اضافه کردن SHA-1
۳. خبر دادن تا کد ورود را بنویسم

---

**Last Updated:** 2026-08-02  
**Updated By:** AI Principal Engineer (Grok)
