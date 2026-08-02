/*
نام فایل: AuthModule.kt
مسیر: app/.../di/
وظیفه: فراهم کردن وابستگی‌های مربوط به احراز هویت با Hilt
نویسنده: AI Principal Engineer
تاریخ: 2026-08-02
*/

package com.kafokokab.app.di

import com.google.firebase.auth.FirebaseAuth
import com.kafokokab.core.data.repository.AuthRepositoryImpl
import com.kafokokab.core.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    }
}
