/*
نام فایل: BirthChartViewModel.kt
مسیر: app/.../ui/chart/
وظیفه: مدیریت وضعیت صفحه چارت تولد و فراخوانی UseCase محاسبه
نویسنده: AI Principal Engineer
تاریخ: 2026-08-08
آخرین تغییر: 2026-08-09 - اولویت با داده واقعی پروفایل کاربر
*/

package com.kafokokab.app.ui.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kafokokab.core.domain.model.astrology.BirthChart
import com.kafokokab.core.domain.model.astrology.ChartSystem
import com.kafokokab.core.domain.repository.ProfileRepository
import com.kafokokab.core.domain.usecase.CalculateBirthChartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BirthChartUiState(
    val isLoading: Boolean = false,
    val chart: BirthChart? = null,
    val errorMessage: String? = null,
    val selectedSystem: ChartSystem = ChartSystem.WESTERN,
    val usingSampleData: Boolean = true
)

@HiltViewModel
class BirthChartViewModel @Inject constructor(
    private val calculateBirthChart: CalculateBirthChartUseCase,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BirthChartUiState())
    val uiState: StateFlow<BirthChartUiState> = _uiState.asStateFlow()

    init {
        loadChart()
    }

    /**
     * بارگذاری چارت.
     * اگر پروفایل کاربر تاریخ تولد معتبر داشته باشد از آن استفاده می‌کند،
     * در غیر این صورت از داده نمونه استفاده می‌شود.
     */
    fun loadChart() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val profile = profileRepository.getProfile()

                val year = profile.birthYear.toIntOrNull()
                val month = profile.birthMonth.toIntOrNull()
                val day = profile.birthDay.toIntOrNull()

                val hasRealData = year != null && month != null && day != null && year > 1900

                val chart = if (hasRealData) {
                    val hour = profile.birthHour.toIntOrNull() ?: 12
                    val minute = profile.birthMinute.toIntOrNull() ?: 0

                    calculateBirthChart(
                        year = year!!,
                        month = month!!,
                        day = day!!,
                        hour = hour,
                        minute = minute,
                        latitude = 35.6892,   // فعلاً تهران – بعداً از شهر واقعی
                        longitude = 51.3890,
                        system = _uiState.value.selectedSystem
                    )
                } else {
                    // داده نمونه
                    calculateBirthChart(
                        year = 1995,
                        month = 7,
                        day = 15,
                        hour = 14,
                        minute = 30,
                        latitude = 35.6892,
                        longitude = 51.3890,
                        system = _uiState.value.selectedSystem
                    )
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        chart = chart,
                        usingSampleData = !hasRealData
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "خطا در محاسبه چارت: ${e.message}"
                    )
                }
            }
        }
    }

    fun changeSystem(system: ChartSystem) {
        _uiState.update { it.copy(selectedSystem = system) }
        loadChart()
    }
}
