# PROJECT_STATE.md

## Current Phase
Phase 2 - Authentication & Onboarding UI

## مرحله فعلی
فاز ۲ - رابط کاربری ورود و تکمیل مشخصات کاربر

---

## Completed
- Phase 1 foundation complete
- LoginScreen implemented
- BirthInfoScreen (Step 1/4) implemented with clean modular components
- Shared Onboarding components (Header, SectionCard, SelectorBox, GenderChip, ContinueButton)
- Mole-analysis privacy decision recorded (no body photos – body-part menu instead)
- Navigation updated for onboarding flow

## انجام شده
- فونداسیون فاز ۱ کامل
- صفحه LoginScreen پیاده‌سازی شد
- صفحه BirthInfoScreen (مرحله ۱ از ۴) با کامپوننت‌های تمیز پیاده‌سازی شد
- کامپوننت‌های مشترک آنبوردینگ ساخته شد
- تصمیم حریم خصوصی خال‌شناسی ثبت شد (بدون عکس بدن – منوی موقعیت بدن)
- ناوبری برای جریان آنبوردینگ به‌روز شد

---

## In Progress
- PersonalInfoScreen (Step 2/4)

## در حال انجام
- صفحه اطلاعات شخصی (مرحله ۲ از ۴)

---

## Blocked
None

## مسدود شده
هیچ

---

## Not Done
- PersonalInfoScreen (نام + کف دست)
- ExtraInfoScreen (چهره + منوی خال‌شناسی بدون عکس)
- ReviewScreen
- Real Google / Phone auth logic
- Home Dashboard matching design

## انجام نشده
- صفحه اطلاعات شخصی
- صفحه اطلاعات تکمیلی (با منوی خال‌شناسی)
- صفحه بررسی نهایی
- منطق واقعی احراز هویت
- داشبورد خانه مطابق طراحی

---

## Next Step
Implement PersonalInfoScreen (Step 2/4) – Name fields + Palm photo placeholders

## مرحله بعد
پیاده‌سازی PersonalInfoScreen (مرحله ۲ از ۴) – فیلدهای نام + محل عکس کف دست

---

## Architecture Notes for Easy Editing
- Onboarding components are shared and live in BirthInfoScreen.kt (can be moved to a common file later)
- All selectors are placeholder clickable boxes (real pickers will be added later)
- Full Persian comments everywhere

---

**Last Updated:** 2026-08-01  
**Updated By:** AI Principal Engineer (Grok)
