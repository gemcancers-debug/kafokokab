/*
نام فایل: OnboardingViewModel.kt
مسیر: app/.../ui/onboarding/
وظیفه: مدیریت وضعیت و داده‌های آنبوردینگ با StateFlow
نویسنده: AI Principal Engineer
تاریخ: 2026-08-01

نکته برای ویرایش بعدی:
- همه تغییرات از طریق متدهای این ViewModel انجام شود
- بعداً می‌توان Repository و DataStore را به آن تزریق کرد
- از Hilt برای تزریق استفاده شده است
*/

package com.kafokokab.app.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kafokokab.core.domain.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel مسئول نگهداری و به‌روزرسانی اطلاعات کاربر در طول آنبوردینگ.
 *
 * استفاده:
 * - در هر صفحه آنبوردینگ این ViewModel را inject کنید
 * - تغییرات را فقط از طریق متدهای update... انجام دهید
 */
@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    // وضعیت فعلی پروفایل کاربر
    private val _profile = MutableStateFlow(UserProfile())
    val profile: StateFlow<UserProfile> = _profile.asStateFlow()

    // ---------- متدهای به‌روزرسانی اطلاعات تولد ----------

    fun updateBirthDate(day: String, month: String, year: String) {
        _profile.update { it.copy(birthDay = day, birthMonth = month, birthYear = year) }
    }

    fun updateBirthTime(hour: String, minute: String, isUnknown: Boolean) {
        _profile.update {
            it.copy(
                birthHour = hour,
                birthMinute = minute,
                isBirthTimeUnknown = isUnknown
            )
        }
    }

    fun updateGender(gender: String) {
        _profile.update { it.copy(gender = gender) }
    }

    fun updateBirthLocation(country: String, province: String, city: String) {
        _profile.update {
            it.copy(
                birthCountry = country,
                birthProvince = province,
                birthCity = city
            )
        }
    }

    // ---------- متدهای به‌روزرسانی اطلاعات فردی ----------

    fun updatePersonalInfo(firstName: String, lastName: String, motherName: String) {
        _profile.update {
            it.copy(
                firstName = firstName,
                lastName = lastName,
                motherName = motherName
            )
        }
    }

    fun updatePalmPhotos(hasLeft: Boolean, hasRight: Boolean) {
        _profile.update {
            it.copy(
                hasLeftPalmPhoto = hasLeft,
                hasRightPalmPhoto = hasRight
            )
        }
    }

    // ---------- متدهای به‌روزرسانی اطلاعات تکمیلی ----------

    fun updateFacePhoto(hasFace: Boolean) {
        _profile.update { it.copy(hasFacePhoto = hasFace) }
    }

    fun updateMolePositions(positions: Set<String>) {
        _profile.update { it.copy(selectedMolePositions = positions) }
    }

    fun toggleMolePosition(position: String) {
        _profile.update { current ->
            val newSet = if (position in current.selectedMolePositions) {
                current.selectedMolePositions - position
            } else {
                current.selectedMolePositions + position
            }
            current.copy(selectedMolePositions = newSet)
        }
    }

    fun updateOptionalInfo(
        eyeColor: String = _profile.value.eyeColor,
        bloodType: String = _profile.value.bloodType,
        heightCm: String = _profile.value.heightCm,
        hairColor: String = _profile.value.hairColor
    ) {
        _profile.update {
            it.copy(
                eyeColor = eyeColor,
                bloodType = bloodType,
                heightCm = heightCm,
                hairColor = hairColor
            )
        }
    }

    // ---------- اتمام آنبوردینگ ----------

    /**
     * وقتی کاربر در صفحه Review تأیید نهایی را زد، این متد را صدا بزنید.
     * بعداً اینجا ذخیره در DataStore/Room هم اضافه می‌شود.
     */
    fun completeOnboarding() {
        viewModelScope.launch {
            _profile.update { it.copy(isOnboardingCompleted = true) }
            // TODO: ذخیره در DataStore یا Room
        }
    }

    /** بازنشانی کامل (برای تست یا خروج از حساب) */
    fun resetProfile() {
        _profile.value = UserProfile()
    }
}
