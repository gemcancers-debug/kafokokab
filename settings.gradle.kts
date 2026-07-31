/*
نام فایل: settings.gradle.kts
وظیفه: تعریف ماژول‌های پروژه و تنظیمات مخازن
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
*/

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Kafokokab"

// ماژول اصلی اپلیکیشن
include(":app")

// لایه‌های Core (Clean Architecture)
include(":core:core-common")
include(":core:core-domain")
include(":core:core-data")
include(":core:core-ui")

// ماژول‌های ویژگی (در فازهای بعدی فعال می‌شوند)
// include(":feature:feature-auth")
// include(":feature:feature-profile")
// include(":feature:feature-birthchart")
