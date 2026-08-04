# PROJECT_STATE.md

## Current Phase
Phase 2 - COMPLETED ✅

## مرحله فعلی
فاز ۲ - تکمیل شد ✅

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
- **All remaining screens fixed**: HomeScreen, BirthInfoScreen, PersonalInfoScreen, ExtraInfoScreen, ReviewScreen
  - Correct import: `import androidx.compose.ui.Modifier`
- **Successful CI Build** at SHA `1e68e7f` (Build Debug APK - conclusion: success)

## انجام شده
- UI کامل آنبوردینگ + داشبورد خانه
- مدل و ViewModel آنبوردینگ
- پایه Firebase
- لایه احراز هویت کامل
- اتصال دکمه ورود با گوگل به منطق واقعی
- کامپوننت‌های GlassCard و PremiumBlurBox
- وابستگی‌های Compose در core-ui به صورت api
- **همه صفحات باقی‌مانده اصلاح شدند**
- **بیلد موفق CI** در SHA `1e68e7f`

---

## In Progress
- None (Phase 2 finished)

## در حال انجام
- هیچ (فاز ۲ تمام شد)

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

## Root Cause of Previous Build Failures (Resolved)
Wrong import used in multiple files:
`import androidx.compose.ui.Modifier.modifier`

Correct import is:
`import androidx.compose.ui.Modifier`

All instances have been corrected. Build is now green.

---

## Next Step (Recommended)
1. Download the successful APK from GitHub Actions artifacts and test on device
2. Connect OnboardingViewModel fully to the UI screens (if not already fully bound)
3. Or start Phase 3: Astrology Core Engine (planet data, birth chart calculation foundation)

## مرحله بعد (پیشنهادی)
۱. دانلود APK موفق از Artifacts گیت‌هاب و تست روی دستگاه
۲. اتصال کامل OnboardingViewModel به صفحات UI
۳. یا شروع فاز ۳: موتور اصلی نجوم (داده سیارات، پایه محاسبه چارت تولد)

---

**Last Updated:** 2026-08-04  
**Updated By:** AI Principal Engineer (Grok)  
**Latest Successful SHA:** 1e68e7f218aa9c707a671395d93ac73e251aeaa2
