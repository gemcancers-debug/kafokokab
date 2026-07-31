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

## Current Priority
Phase 0 is complete.  
Next: Phase 1 — Multi-module Clean Architecture skeleton + Theme + Navigation.

## Development Constraints
- Code must be editable in a simple text editor (Acode).
- Dependencies must be kept as light as possible for mobile GitHub Actions builds.
- No mock data in production code paths.
