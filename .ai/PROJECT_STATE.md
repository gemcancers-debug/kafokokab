# PROJECT_STATE.md

## Current Phase
Phase 2 - Fixing core-ui compilation + Google Sign-In ready

## مرحله فعلی
فاز ۲ - رفع خطای کامپایل core-ui + ورود با گوگل آماده

---

## Completed
- Full Onboarding UI + Home Dashboard
- UserProfile + OnboardingViewModel
- Firebase foundation (google-services.json)
- AuthRepository + AuthRepositoryImpl
- AuthViewModel
- GoogleSignInHelper
- LoginScreen connected to real Google Sign-In flow
- GlassCard + PremiumBlurBox components

## انجام شده
- UI کامل + داشبورد
- مدل و ViewModel آنبوردینگ
- پایه Firebase
- لایه احراز هویت کامل
- اتصال دکمه ورود با گوگل به منطق واقعی
- کامپوننت‌های GlassCard و PremiumBlurBox

---

## In Progress
- Fixing Unresolved reference 'Modifier' in core-ui module
- Verifying Web Client ID (default_web_client_id in strings.xml)

## در حال انجام
- رفع خطای Unresolved reference 'Modifier' در ماژول core-ui
- بررسی Web Client ID

---

## Blocked / Attention Needed
- Build currently fails on core-ui:compileDebugKotlin because of Modifier import
- Attempted fix: changed Compose dependencies from implementation to api + forced clean rewrite of the two component files
- `default_web_client_id` in strings.xml is still a placeholder.
  If Google Sign-In fails with "10:" or invalid token errors:
  1. Go to Firebase Console → Project Settings
  2. Re-download google-services.json after enabling Google provider
  3. Find the client_id with client_type: 3 (Web client)
  4. Put that value into `app/src/main/res/values/strings.xml` → default_web_client_id

## نیاز به توجه
- بیلد فعلی روی core-ui:compileDebugKotlin به خاطر Modifier شکست می‌خورد
- تلاش برای رفع: تغییر dependencyهای Compose به api + بازنویسی تمیز فایل‌ها
- مقدار default_web_client_id هنوز placeholder است.

---

## Next Step
1. Wait for new GitHub Actions build after this push
2. If still fails → dig deeper into Compose setup for library modules
3. Then test Google Sign-In on device
4. Bind OnboardingViewModel or start Phase 3

## مرحله بعد
۱. منتظر بیلد جدید GitHub Actions بعد از این push
۲. اگر هنوز شکست خورد → بررسی عمیق‌تر تنظیمات Compose برای ماژول library
۳. سپس تست ورود با گوگل روی گوشی
۴. اتصال ViewModel آنبوردینگ یا شروع فاز ۳

---

**Last Updated:** 2026-08-03  
**Updated By:** AI Principal Engineer (Grok)
