/*
نام فایل: ProfileModule.kt
مسیر: core/core-data/.../di/
وظیفه: تزریق ProfileRepository
نویسنده: AI Principal Engineer
تاریخ: 2026-08-09
*/

package com.kafokokab.core.data.di

import com.kafokokab.core.data.profile.ProfileRepositoryImpl
import com.kafokokab.core.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository
}
