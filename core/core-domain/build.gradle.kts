/*
نام فایل: core/core-domain/build.gradle.kts
وظیفه: لایه Domain (UseCaseها، مدل‌های دامنه، اینترفیس Repository)
این لایه هیچ وابستگی به Android ندارد.
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
*/

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.kafokokab.core.domain"
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
}

dependencies {
    implementation(project(":core:core-common"))
    implementation(libs.kotlinx.coroutines.android)
}
