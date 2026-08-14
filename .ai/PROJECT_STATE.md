# PROJECT_STATE.md

## Current Phase
Phase 3 - Birth input compile fix + real chart engine

## مرحله فعلی
فاز ۳ - رفع خطای کامپایل BirthInfoScreen + موتور واقعی چارت

---

## Completed
- **BirthInfoScreen compile error fixed**: `Row(Modifier =` → `Row(modifier =` (typo that broke assembleDebug)
- Working day/month/year/hour/minute/city pickers (AlertDialog)
- City list now comes from `IranCities.allCityNames()` (same source as chart engine)
- RTL forced: MainActivity + Theme + XML
- Month stored as 1–12 numeric for FormulaAstrologyCalculator
- FormulaAstrologyCalculator (Meeus-style, offline) + PersianDateConverter + IranCities
- No mock chart data in BirthChartViewModel

## انجام شده
- رفع خطای کامپایل: پارامتر اشتباه `Modifier` به‌جای `modifier`
- پیکرهای تاریخ/زمان/شهر کار می‌کنند
- لیست شهر با موتور نجوم هم‌منبع شد
- RTL اجباری
- موتور نجوم واقعی آفلاین

## In Progress
- Verify birth date pickers on new APK after successful GitHub Actions build
- Polish remaining onboarding screens if needed

## Next Step
1. Wait for GitHub Actions green build after this push
2. Install new APK and test tapping year/month/day boxes
3. If OK → polish PersonalInfo / ExtraInfo / Review for consistent RTL + validation

**Last Updated:** 2026-08-14
