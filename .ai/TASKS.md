# TASKS.md

## Phase 0 - Bootstrap
- [x] Repository accessible with write permission
- [x] Create `.ai/` documentation system
- [x] Write PROJECT_STATE.md (bilingual)
- [x] Write ARCHITECTURE.md
- [x] Write TASKS.md
- [x] Write CHANGELOG.md
- [x] Write UI_GUIDELINES.md
- [x] Write AI_NOTES.md
- [x] Add .gitignore
- [x] Add proper README

## Phase 1 - Project Skeleton + Design System + Navigation + CI
- [x] Create version catalog (`gradle/libs.versions.toml`)
- [x] Create multi-module structure (settings.gradle.kts)
- [x] Create `app` module
- [x] Create `core-ui`, `core-domain`, `core-data`, `core-common`
- [x] Setup basic Theme (Dark Galaxy colors)
- [x] Typography system
- [x] GlassCard composable
- [x] PremiumBlurBox composable
- [x] Navigation Host (type-safe)
- [x] Hilt basic setup (AppModule)
- [x] GitHub Actions workflow for building APK
- [ ] Gradle Wrapper (gradlew) – still needed for reliable CI

## Phase 2 - Authentication & Profile
- [ ] Google Sign-In
- [ ] Iranian Phone OTP (later)
- [ ] User Profile screen
- [ ] Birth data input form

## Phase 3 - Astrology Core Engine
- [ ] Offline planetary data strategy & storage
- [ ] Birth Chart calculation engine (Western)
- [ ] Vedic Chart support
- [ ] Transits engine
- [ ] Chart UI (Glassmorphism)

## Phase 4 - Premium & Monetization
- [ ] Google Play Billing integration
- [ ] Feature gating logic

## Phase 5 - Additional Modules
- [ ] Numerology + Abjad
- [ ] Chinese Astrology
- [ ] Tarot
- [ ] Hafez
- [ ] Simple Palm / Face analysis
- [ ] Dream Journal / Personal Timeline

---

**Status Legend**
- [ ] TODO
- [x] DONE
- [~] IN PROGRESS
- [!] BLOCKED
