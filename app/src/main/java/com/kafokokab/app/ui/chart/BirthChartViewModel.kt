/*
نام فایل: BirthChartViewModel.kt
مسیر: app/.../ui/chart/
وظیفه: مدیریت وضعیت صفحه چارت تولد و فراخوانی UseCase محاسبه
نویسنده: AI Principal Engineer
تاریخ: 2026-08-08
آخرین تغییر: 2026-08-13 - حذف کامل داده Mock؛ فقط تاریخ واقعی پروفایل + مختصات شهر

قوانین:
- هیچ تاریخ نمونه‌ای استفاده نمی‌شود
- اگر تاریخ تولد نباشد، پیام راهنما نمایش داده می‌شود
- تاریخ شمسی توسط موتور به میلادی تبدیل می‌شود
*/

package com.kafokokab.app.ui.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kafokokab.core.domain.geo.IranCities
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
    /** true فقط وقتی تاریخ تولد در پروفایل نیست */
    val needsBirthInfo: Boolean = false
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
     * بارگذاری چارت فقط از داده واقعی پروفایل.
     * داده Mock / نمونه استفاده نمی‌شود.
     */
    fun loadChart() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null, needsBirthInfo = false)
            }
            try {
                val profile = profileRepository.getProfile()

                val year = profile.birthYear.toIntOrNull()
                val month = profile.birthMonth.toIntOrNull()
                val day = profile.birthDay.toIntOrNull()

                // سال شمسی (مثلاً ۱۳۶۸) یا میلادی (مثلاً ۱۹۹۰) هر دو معتبرند
                val hasRealData = year != null && month != null && day != null &&
                    year in 1200..2100 && month in 1..12 && day in 1..31

                if (!hasRealData) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            chart = null,
                            needsBirthInfo = true,
                            errorMessage = "برای محاسبه چارت، تاریخ تولد را در پروفایل کامل کنید."
                        )
                    }
                    return@launch
                }

                val hour = profile.birthHour.toIntOrNull() ?: 12
                val minute = profile.birthMinute.toIntOrNull() ?: 0

                // مختصات واقعی شهر تولد (در صورت نبود → تهران)
                val location = IranCities.resolve(profile.birthCity, profile.birthProvince)

                val chart = calculateBirthChart(
                    year = year!!,
                    month = month!!,
                    day = day!!,
                    hour = hour,
                    minute = minute,
                    latitude = location.latitude,
                    longitude = location.longitude,
                    system = _uiState.value.selectedSystem
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        chart = chart,
                        needsBirthInfo = false,
                        errorMessage = null
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
