/*
نام فایل: ProfileRepositoryImpl.kt
مسیر: core/core-data/.../profile/
وظیفه: پیاده‌سازی ذخیره پروفایل با DataStore Preferences
نویسنده: AI Principal Engineer
تاریخ: 2026-08-09

نکته:
- از DataStore استفاده شده چون داده ساده key-value است
- بعداً در صورت نیاز می‌توان به Room مهاجرت کرد
*/

package com.kafokokab.core.data.profile

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kafokokab.core.domain.model.UserProfile
import com.kafokokab.core.domain.repository.ProfileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.profileDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_profile")

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ProfileRepository {

    private object Keys {
        val BIRTH_DAY = stringPreferencesKey("birth_day")
        val BIRTH_MONTH = stringPreferencesKey("birth_month")
        val BIRTH_YEAR = stringPreferencesKey("birth_year")
        val BIRTH_HOUR = stringPreferencesKey("birth_hour")
        val BIRTH_MINUTE = stringPreferencesKey("birth_minute")
        val IS_BIRTH_TIME_UNKNOWN = booleanPreferencesKey("is_birth_time_unknown")
        val GENDER = stringPreferencesKey("gender")
        val BIRTH_COUNTRY = stringPreferencesKey("birth_country")
        val BIRTH_PROVINCE = stringPreferencesKey("birth_province")
        val BIRTH_CITY = stringPreferencesKey("birth_city")

        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val MOTHER_NAME = stringPreferencesKey("mother_name")

        val HAS_LEFT_PALM = booleanPreferencesKey("has_left_palm")
        val HAS_RIGHT_PALM = booleanPreferencesKey("has_right_palm")
        val HAS_FACE_PHOTO = booleanPreferencesKey("has_face_photo")

        val MOLE_POSITIONS = stringSetPreferencesKey("mole_positions")

        val EYE_COLOR = stringPreferencesKey("eye_color")
        val BLOOD_TYPE = stringPreferencesKey("blood_type")
        val HEIGHT_CM = stringPreferencesKey("height_cm")
        val HAIR_COLOR = stringPreferencesKey("hair_color")

        val IS_ONBOARDING_COMPLETED = booleanPreferencesKey("is_onboarding_completed")
    }

    override val profileFlow: Flow<UserProfile> = context.profileDataStore.data.map { prefs ->
        prefs.toUserProfile()
    }

    override suspend fun saveProfile(profile: UserProfile) {
        context.profileDataStore.edit { prefs ->
            prefs[Keys.BIRTH_DAY] = profile.birthDay
            prefs[Keys.BIRTH_MONTH] = profile.birthMonth
            prefs[Keys.BIRTH_YEAR] = profile.birthYear
            prefs[Keys.BIRTH_HOUR] = profile.birthHour
            prefs[Keys.BIRTH_MINUTE] = profile.birthMinute
            prefs[Keys.IS_BIRTH_TIME_UNKNOWN] = profile.isBirthTimeUnknown
            prefs[Keys.GENDER] = profile.gender
            prefs[Keys.BIRTH_COUNTRY] = profile.birthCountry
            prefs[Keys.BIRTH_PROVINCE] = profile.birthProvince
            prefs[Keys.BIRTH_CITY] = profile.birthCity

            prefs[Keys.FIRST_NAME] = profile.firstName
            prefs[Keys.LAST_NAME] = profile.lastName
            prefs[Keys.MOTHER_NAME] = profile.motherName

            prefs[Keys.HAS_LEFT_PALM] = profile.hasLeftPalmPhoto
            prefs[Keys.HAS_RIGHT_PALM] = profile.hasRightPalmPhoto
            prefs[Keys.HAS_FACE_PHOTO] = profile.hasFacePhoto

            prefs[Keys.MOLE_POSITIONS] = profile.selectedMolePositions

            prefs[Keys.EYE_COLOR] = profile.eyeColor
            prefs[Keys.BLOOD_TYPE] = profile.bloodType
            prefs[Keys.HEIGHT_CM] = profile.heightCm
            prefs[Keys.HAIR_COLOR] = profile.hairColor

            prefs[Keys.IS_ONBOARDING_COMPLETED] = profile.isOnboardingCompleted
        }
    }

    override suspend fun getProfile(): UserProfile {
        return context.profileDataStore.data.first().toUserProfile()
    }

    override suspend fun clearProfile() {
        context.profileDataStore.edit { it.clear() }
    }

    private fun Preferences.toUserProfile(): UserProfile {
        return UserProfile(
            birthDay = this[Keys.BIRTH_DAY] ?: "",
            birthMonth = this[Keys.BIRTH_MONTH] ?: "",
            birthYear = this[Keys.BIRTH_YEAR] ?: "",
            birthHour = this[Keys.BIRTH_HOUR] ?: "",
            birthMinute = this[Keys.BIRTH_MINUTE] ?: "",
            isBirthTimeUnknown = this[Keys.IS_BIRTH_TIME_UNKNOWN] ?: false,
            gender = this[Keys.GENDER] ?: "",
            birthCountry = this[Keys.BIRTH_COUNTRY] ?: "ایران",
            birthProvince = this[Keys.BIRTH_PROVINCE] ?: "",
            birthCity = this[Keys.BIRTH_CITY] ?: "",

            firstName = this[Keys.FIRST_NAME] ?: "",
            lastName = this[Keys.LAST_NAME] ?: "",
            motherName = this[Keys.MOTHER_NAME] ?: "",

            hasLeftPalmPhoto = this[Keys.HAS_LEFT_PALM] ?: false,
            hasRightPalmPhoto = this[Keys.HAS_RIGHT_PALM] ?: false,
            hasFacePhoto = this[Keys.HAS_FACE_PHOTO] ?: false,

            selectedMolePositions = this[Keys.MOLE_POSITIONS] ?: emptySet(),

            eyeColor = this[Keys.EYE_COLOR] ?: "",
            bloodType = this[Keys.BLOOD_TYPE] ?: "",
            heightCm = this[Keys.HEIGHT_CM] ?: "",
            hairColor = this[Keys.HAIR_COLOR] ?: "",

            isOnboardingCompleted = this[Keys.IS_ONBOARDING_COMPLETED] ?: false
        )
    }
}
