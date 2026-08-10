/*
نام فایل: HomeViewModel.kt
مسیر: app/.../ui/home/
وظیفه: مدیریت داده صفحه اصلی (نام کاربر و خلاصه پروفایل)
نویسنده: AI Principal Engineer
تاریخ: 2026-08-10

این ViewModel از ProfileRepository می‌خواند تا نام واقعی کاربر نمایش داده شود.
*/

package com.kafokokab.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kafokokab.core.domain.model.UserProfile
import com.kafokokab.core.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class HomeUiState(
    val userName: String = "کاربر",
    val hasBirthInfo: Boolean = false,
    val birthSummary: String = ""
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    profileRepository: ProfileRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = profileRepository.profileFlow
        .map { profile ->
            HomeUiState(
                userName = profile.fullName.ifBlank { "کاربر" },
                hasBirthInfo = profile.hasBasicBirthInfo,
                birthSummary = buildBirthSummary(profile)
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

    private fun buildBirthSummary(profile: UserProfile): String {
        if (!profile.hasBasicBirthInfo) return ""
        return listOfNotNull(
            profile.birthDay.takeIf { it.isNotBlank() },
            profile.birthMonth.takeIf { it.isNotBlank() },
            profile.birthYear.takeIf { it.isNotBlank() }
        ).joinToString("/") + if (profile.birthCity.isNotBlank()) " - ${profile.birthCity}" else ""
    }
}
