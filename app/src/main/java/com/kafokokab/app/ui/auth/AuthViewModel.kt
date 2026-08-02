/*
نام فایل: AuthViewModel.kt
مسیر: app/.../ui/auth/
وظیفه: مدیریت وضعیت ورود و خروج کاربر
نویسنده: AI Principal Engineer
تاریخ: 2026-08-02
*/

package com.kafokokab.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kafokokab.core.domain.model.AuthUser
import com.kafokokab.core.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * وضعیت‌های ممکن صفحه ورود
 */
sealed class AuthUiState {
    data object Idle : AuthUiState()
    data object Loading : AuthUiState()
    data class Success(val user: AuthUser) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val currentUser = authRepository.currentUser

    /**
     * بعد از دریافت idToken از Google Sign-In این متد را صدا بزنید.
     */
    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _uiState.update { AuthUiState.Loading }
            val result = authRepository.signInWithGoogle(idToken)
            _uiState.update {
                result.fold(
                    onSuccess = { AuthUiState.Success(it) },
                    onFailure = { AuthUiState.Error(it.message ?: "خطا در ورود با گوگل") }
                )
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _uiState.update { AuthUiState.Idle }
        }
    }

    fun resetState() {
        _uiState.update { AuthUiState.Idle }
    }

    fun isLoggedIn(): Boolean = authRepository.isUserLoggedIn()
}
