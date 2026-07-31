# ARCHITECTURE.md

## Decision Log

### Decision 001 - Overall Architecture Style
**Date:** 2026-07-31

**Problem**  
Need a maintainable, testable and scalable architecture for a complex long-lived astrology + self-discovery super-app (Android first, iOS later).

**Possible Solutions**
1. Simple single-module MVVM
2. Clean Architecture (Presentation / Domain / Data) + multi-module
3. MVI + modular

**Selected Solution**  
Clean Architecture + Multi-module + MVVM + Repository + UseCase

**Reason**  
Clear separation of concerns, high testability of Domain layer, independent feature development, and ready for future Compose Multiplatform.

**Advantages**
- Domain layer has zero Android dependencies → easy unit tests
- Feature modules can be developed in isolation
- Long-term maintainability

**Disadvantages**
- More initial boilerplate
- Requires strict discipline

**Future Improvements**
- Shared KMP module when iOS work starts
- Evaluate Circuit / Decompose if navigation complexity grows

---

### Decision 002 - Planned Module Structure

```
kafokokab/
├── app/                          # Entry point, DI, Navigation Host
├── core/
│   ├── core-ui/                  # Design System, Theme, GlassCard, PremiumBlurBox
│   ├── core-domain/              # Shared domain models & repository interfaces
│   ├── core-data/                # Room, DataStore, network, repository implementations
│   └── core-common/              # Result, extensions, utilities
├── feature/
│   ├── feature-auth/
│   ├── feature-profile/
│   ├── feature-birthchart/
│   ├── feature-horoscope/
│   ├── feature-numerology/
│   ├── feature-tarot/
│   ├── feature-hafez/
│   ├── feature-palm/
│   ├── feature-face/
│   └── feature-premium/
└── .ai/                          # Project brain (this documentation)
```

---

### Decision 003 - Database Strategy
**Selected:** Room  
**Reason:** Mature ecosystem, excellent Compose integration, perfect for offline planetary/transit data.  
Will re-evaluate SQLDelight only if strong multiplatform shared database requirement appears.

---

### Decision 004 - Dependency Injection
**Selected:** Hilt (for Android)  
Will migrate to a KMP-friendly solution (Koin or official) when multiplatform becomes active.

---

### Decision 005 - UI Design System
Glassmorphism + Dark Galaxy theme with Neon Pink / Mystic Purple / Gold accents.  
Full RTL + Vazirmatn font mandatory.

---

**Last Updated:** 2026-07-31
