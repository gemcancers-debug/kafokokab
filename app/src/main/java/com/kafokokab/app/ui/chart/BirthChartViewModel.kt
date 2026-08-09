/*
نام فایل: BirthChartViewModel.kt
مسیر: app/.../ui/chart/
وظیفه: مدیریت وضعیت صفحه چارت تولد و فراخوانی UseCase محاسبه
نویسنده: AI Principal Engineer
تاریخ: 2026-08-08

نکته:
- فعلاً از داده نمونه استفاده می‌کند چون UserProfile هنوز در DataStore ذخیره نمی‌شود
- بعداً می‌توان از OnboardingViewModel یا Repository پروفایل تغذیه کرد
*/

package com.kafokokab.app.ui.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kafokokab.core.domain.model.astrology.BirthChart
import com.kafokokab.core.domain.model.astrology.ChartSystem
import com.kafokokab.core.domain.usecase.CalculateBirthChartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * وضعیت صفحه چارت تولد
 */
data class BirthChartUiState(
    val isLoading: Boolean = false,
    val chart: BirthChart? = null,
    val errorMessage: String? = null,
    val selectedSystem: ChartSystem = ChartSystem.WESTERN
)

@HiltViewModel
class BirthChartViewModel @Inject constructor(
    private val calculateBirthChart: CalculateBirthChartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BirthChartUiState())
    val uiState: StateFlow<BirthChartUiState> = _uiState.asStateFlow()

    init {
        // محاسبه اولیه با داده نمونه (تهران - تاریخ نمونه)
        loadSampleChart()
    }

    /**
     * محاسبه چارت با داده نمونه.
     * بعداً این متد از پروفایل واقعی کاربر تغذیه می‌شود.
     */
    fun loadSampleChart() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val chart = calculateBirthChart(
                    year = 1995,
                    month = 7,
                    day = 15,
                    hour = 14,
                    minute = 30,
                    latitude = 35.6892,   // تهران
                    longitude = 51.3890,
                    system = _uiState.value.selectedSystem
                )
                _uiState.update {
                    it.copy(isLoading = false, chart = chart)
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

    /** تغییر سیستم محاسبه (غربی / ودیک) */
    fun changeSystem(system: ChartSystem) {
        _uiState.update { it.copy(selectedSystem = system) }
        loadSampleChart()
    }
}
