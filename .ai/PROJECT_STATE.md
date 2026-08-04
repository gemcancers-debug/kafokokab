# PROJECT_STATE.md

## Current Phase
Phase 2 - COMPLETED ✅ (Auth UX polish in progress)

## مرحله فعلی
فاز ۲ - تکمیل شد ✅ (بهبود تجربه ورود در حال انجام)

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
- **Auth UX improvements (2026-08-04)**:
  - Clear Persian error messages for Google Sign-In failures
  - Surface ApiException status codes (especially 10 = DEVELOPER_ERROR)
  - Phone login button shows "coming soon" toast

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
- **بهبود UX ورود (۲۰۲۶-۰۸-۰۴)**:
  - پیام خطای واضح فارسی برای شکست Google Sign-In
  - نمایش کد وضعیت ApiException (به‌خصوص ۱۰)
  - دکمه شماره تلفن پیام «به‌زودی» نشان می‌دهد

---

## In Progress
- Waiting for user to configure real Web Client ID in Firebase

## در حال انجام
- منتظر تنظیم Web Client ID واقعی در Firebase توسط کاربر

---

## Blocked / Attention Needed
- `default_web_client_id` in strings.xml is still a placeholder.
- `google-services.json` has empty `oauth_client: []`.
  **Required steps for Google Sign-In to work:**
  1. Firebase Console → Authentication → Sign-in method → Enable **Google**
  2. Project Settings → download fresh `google-services.json`
  3. Replace `app/google-services.json`
  4. Copy the `client_id` with `client_type: 3` (Web client)
  5. Put it into `app/src/main/res/values/strings.xml` → `default_web_client_id`
  6. Rebuild & reinstall APK

## نیاز به توجه
- مقدار default_web_client_id هنوز placeholder است.
- فایل google-services.json بخش oauth_client خالی است.

---

## Notes on current behavior
- **Google Sign-In**: Account picker works; after selection fails silently until Web Client ID is fixed. Now shows clear Toast with error code.
- **Phone login**: No real OTP yet. Navigates to onboarding for testing; shows "coming soon" message.
- **Face camera in ExtraInfo**: UI-only toggle (no real camera) by privacy design.
- **Mole selection chips**: Local state only; should be tappable.

---

## Next Step (Recommended)
1. User configures Firebase Google provider + Web Client ID
2. Rebuild APK and retest Google Sign-In
3. Then: fully bind OnboardingViewModel OR start Phase 3 (Astrology Core)

## مرحله بعد (پیشنهادی)
۱. کاربر Google provider و Web Client ID را در Firebase تنظیم کند
۲. بیلد مجدد و تست ورود با جیمیل
۳. سپس: اتصال کامل OnboardingViewModel یا شروع فاز ۳

---

**Last Updated:** 2026-08-04  
**Updated By:** AI Principal Engineer (Grok)
