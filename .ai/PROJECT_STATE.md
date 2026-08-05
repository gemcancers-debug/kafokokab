# PROJECT_STATE.md

## Current Phase
Phase 2 - COMPLETED ✅ + OnboardingViewModel fully bound

## مرحله فعلی
فاز ۲ - تکمیل شد ✅ + اتصال کامل ViewModel آنبوردینگ

---

## Completed
- Full Onboarding UI + Home Dashboard
- UserProfile + OnboardingViewModel
- Firebase foundation (google-services.json)
- AuthRepository + AuthRepositoryImpl
- AuthViewModel + GoogleSignInHelper
- LoginScreen + improved error feedback
- GlassCard + PremiumBlurBox
- All Modifier import fixes + successful CI build
- **OnboardingViewModel bound to all 4 screens** (2026-08-05)
  - Nested `onboarding` Nav graph for shared ViewModel scope
  - BirthInfo / PersonalInfo / ExtraInfo / Review read & write `UserProfile`
  - Review shows live data (no more hardcoded samples)
  - `completeOnboarding()` on final confirm

## انجام شده
- UI کامل آنبوردینگ + داشبورد
- مدل و ViewModel آنبوردینگ
- پایه Firebase و احراز هویت
- بهبود پیام خطای Google Sign-In
- **اتصال کامل OnboardingViewModel به هر ۴ صفحه**

---

## In Progress
- Waiting for user to configure Firebase Web Client ID

## در حال انجام
- منتظر تنظیم Web Client ID توسط کاربر در Firebase

---

## Blocked / Attention Needed
- `default_web_client_id` still placeholder
- `google-services.json` has empty `oauth_client: []`
  Steps:
  1. Enable Google in Firebase Authentication
  2. Re-download google-services.json
  3. Copy client_type:3 client_id into strings.xml
  4. Rebuild APK

## نیاز به توجه
- Web Client ID هنوز placeholder است (کاربر در حال تنظیم Firebase)

---

## Next Step
1. User finishes Firebase Google Sign-In setup
2. Rebuild & test full flow (login → onboarding → home)
3. Then Phase 3: Astrology Core Engine

## مرحله بعد
۱. اتمام تنظیم Firebase توسط کاربر
۲. بیلد و تست جریان کامل
۳. شروع فاز ۳: موتور نجوم

---

**Last Updated:** 2026-08-05  
**Updated By:** AI Principal Engineer (Grok)
