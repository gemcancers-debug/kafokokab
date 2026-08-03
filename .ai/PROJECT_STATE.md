# PROJECT_STATE.md

## Current Phase
Phase 2 - Fixing Modifier import across entire app module

## مرحله فعلی
فاز ۲ - رفع import اشتباه Modifier در کل ماژول app

---

## Completed
- Full Onboarding UI + Home Dashboard
- UserProfile + OnboardingViewModel
- Firebase foundation (google-services.json)
- AuthRepository + AuthRepositoryImpl
- AuthViewModel
- GoogleSignInHelper
- LoginScreen connected to real Google Sign-In flow
- GlassCard + PremiumBlurBox (correct import)
- core-ui Compose deps as api

## انجام شده
- UI کامل + داشبورد
- مدل و ViewModel آنبوردینگ
- پایه Firebase
- لایه احراز هویت کامل
- اتصال دکمه ورود با گوگل به منطق واقعی
- کامپوننت‌های GlassCard و PremiumBlurBox
- وابستگی‌های Compose در core-ui به صورت api

---

## In Progress
- Fixed wrong import `androidx.compose.ui.Modifier.modifier` → `androidx.compose.ui.Modifier` in all 8 app module files
- Waiting for GitHub Actions build after this push

## در حال انجام
- اصلاح import اشتباه Modifier در ۸ فایل ماژول app
- منتظر بیلد GitHub Actions بعد از این push

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

## Root Cause of Build Failures
Previous commits incorrectly used:
`import androidx.compose.ui.Modifier.modifier`
Correct is:
`import androidx.compose.ui.Modifier`

This affected MainActivity, AppNavHost, LoginScreen, HomeScreen, and all Onboarding screens.

## علت اصلی شکست بیلدها
import اشتباه در فایل‌های UI

---

## Next Step
1. Wait for new GitHub Actions build
2. If green → download APK and test on device
3. Then bind OnboardingViewModel or start Phase 3 (Astrology Core)

## مرحله بعد
۱. منتظر بیلد جدید
۲. اگر سبز شد → دانلود APK و تست
۳. سپس اتصال ViewModel یا شروع فاز ۳

---

**Last Updated:** 2026-08-03  
**Updated By:** AI Principal Engineer (Grok)
