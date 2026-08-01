# AI_NOTES.md

## Critical Context for All Future Sessions

- Developer works 100% from Android phone (Termux + Acode + GitHub CLI).
- All important code comments must be in Persian.
- PROJECT_STATE.md is the single Source of Truth.
- Work strictly phase-by-phase. End every step with «ادامه بدم؟».

## Important Design Decision – Mole Analysis (خال‌شناسی)

**Date:** 2026-08-01

چون اپلیکیشن در بازار ایران منتشر می‌شود:

- **هیچ عکسی از بدن برای خال‌شناسی گرفته نمی‌شود.**
- منوی شیشه‌ای زیبا با لیست موقعیت‌های بدن.
- پوشش کامل بدن با تفکیک چپ و راست.
- **حذف کامل:** نشیمنگاه، آلت جنسی و هر قسمت حساس دیگر.
- قسمت سینه با لحن خنثی و مردانه‌تر: **«قفسه سینه»**.
- توضیح کوتاه + انتخاب چندتایی + ادامه.

لیست موقعیت‌ها در `MoleBodyParts` داخل ExtraInfoScreen.kt قابل ویرایش آسان است.
