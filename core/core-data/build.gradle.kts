/*
نام فایل: core/core-data/build.gradle.kts
وظیفه: لایه Data (Room, DataStore, Repository Implementation)
نویسنده: AI Principal Engineer
تاریخ: 2026-07-31
آخرین تغییر: 2026-08-11 - اضافه شدن Hilt برای حل خطای Unresolved reference dagger در ماژول‌های DI
*/

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)   // لازم برای @Module, @Binds, @Provides, @Inject
}

android {
    namespace = "com.kafokokab.core.data"
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
    implementation(project(":core:core-domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Firebase Auth (برای AuthRepositoryImpl)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // Hilt – ضروری برای DI در این ماژول
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
