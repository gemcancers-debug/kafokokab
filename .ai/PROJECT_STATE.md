# PROJECT_STATE.md

## Current Phase
Phase 2 - Google Sign-In implemented (needs Web Client ID check)

## مرحله فعلی
فاز ۲ - ورود با گوگل پیاده‌سازی شد (نیاز به بررسی Web Client ID)

---

## Completed
- Full Onboarding UI + Home Dashboard
- UserProfile + OnboardingViewModel
- Firebase foundation (google-services.json)
- AuthRepository + AuthRepositoryImpl
- AuthViewModel
- GoogleSignInHelper
- LoginScreen connected to real Google Sign-In flow

## انجام شده
- UI کامل + داشبورد
- مدل و ViewModel آنبوردینگ
- پایه Firebase
- لایه احراز هویت کامل
- اتصال دکمه ورود با گوگل به منطق واقعی

---

## In Progress
- Verifying Web Client ID (default_web_client_id in strings.xml)

## در حال انجام
- بررسی Web Client ID

---

## Blocked / Attention Needed
- `default_web_client_id` in strings.xml is still a placeholder.
  If Google Sign-In fails with "10:" or invalid token errors:
  1. Go to Firebase Console → Project Settings
  2. Re-download google-services.json after enabling Google provider
  3. Find the client_id with client_type: 3 (Web client)
  4. Put that value into `app/src/main/res/values/strings.xml` → default_web_client_id

## نیاز به توجه
- مقدار default_web_client_id هنوز placeholder است.
  اگر ورود خطا داد، فایل google-services.json را دوباره دانلود کنید و Web Client ID را در strings.xml بگذارید.

---

## Next Step
1. Test Google Sign-In on device
2. If it fails → fix Web Client ID
3. Then bind OnboardingViewModel to screens OR start Phase 3

## مرحله بعد
۱. تست ورود با گوگل روی گوشی
۲. در صورت خطا → اصلاح Web Client ID
۳. سپس اتصال ViewModel آنبوردینگ یا شروع فاز ۳

---

**Last Updated:** 2026-08-02  
**Updated By:** AI Principal Engineer (Grok)
