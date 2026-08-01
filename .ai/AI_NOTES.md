# AI_NOTES.md

## Critical Context for All Future Sessions

- Developer works **100% from Android phone** using Termux + Acode + GitHub CLI.
- No Android Studio or desktop computer is available.
- All APK builds must be done via **GitHub Actions**.
- All important code comments must be written in **Persian**.
- Every significant architectural decision must be recorded in `.ai/ARCHITECTURE.md`.
- `.ai/PROJECT_STATE.md` is the single Source of Truth — always update it after meaningful work.
- Never generate the entire application at once. Work strictly phase-by-phase.
- After every logical step, end the message with «ادامه بدم؟» and wait for explicit permission.
- Premium features must always use the reusable `PremiumBlurBox` pattern.
- Offline-first is mandatory for all planetary and transit data.

## Important Design Decision – Mole Analysis (خال‌شناسی)

**Date:** 2026-08-01  
**Decision by:** User + AI Principal Engineer

چون اپلیکیشن قرار است در بازار ایران (مایکت / کافه‌بازار) منتشر شود و گرفتن عکس از بدن برای خال‌شناسی از نظر حریم خصوصی و فرهنگ عمومی مناسب نیست:

- **هیچ عکسی از بدن گرفته نمی‌شود.**
- به جای عکس: یک منوی شیشه‌ای (Glassmorphism) زیبا با لیست کامل موقعیت‌های بدن.
- پوشش کامل بدن با تفکیک چپ و راست (ابرو، گونه، گردن، شانه، کمر، زانو، پاشنه پا و ...).
- بدون استفاده از اصطلاحات نامناسب یا حساس.
- توضیح کوتاه آموزشی + انتخاب موقعیت‌ها + ادامه.

این تصمیم باید در صفحه ExtraInfo (مرحله ۳ آنبوردینگ) رعایت شود.

## Current Priority
Continue Onboarding screens (BirthInfo → PersonalInfo → ExtraInfo → Review).
