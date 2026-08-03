# PROJECT_STATE.md

## Current Phase
Phase 2 - Fixing core-ui compilation (Modifier import)

## مرحله فعلی
فاز ۲ - رفع خطای کامپایل core-ui (import اشتباه Modifier)

---

## Completed
- Full Onboarding UI + Home Dashboard
- UserProfile + OnboardingViewModel
- Firebase foundation (google-services.json)
- AuthRepository + AuthRepositoryImpl
- AuthViewModel
- GoogleSignInHelper
- LoginScreen connected to real Google Sign-In flow
- GlassCard + PremiumBlurBox components (with correct import)

## انجام شده
- UI کامل + داشبورد
- مدل و ViewModel آنبوردینگ
- پایه Firebase
- لایه احراز هویت کامل
- اتصال دکمه ورود با گوگل به منطق واقعی
- کامپوننت‌های GlassCard و PremiumBlurBox (با import صحیح)

---

## In Progress
- Verifying build after correct Modifier import fix
- Verifying Web Client ID (default_web_client_id in strings.xml)

## در حال انجام
- بررسی بیلد بعد از اصلاح import صحیح Modifier
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

---

## Next Step
1. Wait for new GitHub Actions build after this push
2. If green → download APK and test on device
3. Then bind OnboardingViewModel or start Phase 3 (Astrology Core)

## مرحله بعد
۱. منتظر بیلد جدید GitHub Actions بعد از این push
۲. اگر سبز شد → دانلود APK و تست روی گوشی
۳. سپس اتصال ViewModel آنبوردینگ یا شروع فاز ۳

---

**Last Updated:** 2026-08-03  
**Updated By:** AI Principal Engineer (Grok)
