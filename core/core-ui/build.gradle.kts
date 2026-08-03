/*
نام فایل: core/core-ui/build.gradle.kts
وظیفه: Design System، Theme، کامپوننت‌های مشترک UI (GlassCard, PremiumBlurBox)
نویسنده: AI Principal Engineer
تاریخ: 2026-08-03
*/

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.kafokokab.core.ui"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core:core-common"))

    // Core Android
    implementation(libs.androidx.core.ktx)

    // Compose BOM + UI (api تا در ماژول‌های دیگر هم در دسترس باشد)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.material3)
    api(libs.androidx.material.icons.extended)

    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
}
