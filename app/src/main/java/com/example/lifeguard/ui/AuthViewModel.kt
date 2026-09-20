package com.lifeguard.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AuthMode { LOGIN, REGISTER, PHONE }

data class AuthUiState(
    val mode: AuthMode = AuthMode.LOGIN,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val phone: String = "",
    val otp: String = "",
    val otpSent: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAuthenticated: Boolean = false
)

class AuthViewModel : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    fun onModeChange(mode: AuthMode) = _state.update { it.copy(mode = mode, error = null) }
    fun onNameChange(v: String) = _state.update { it.copy(name = v) }
    fun onEmailChange(v: String) = _state.update { it.copy(email = v) }
    fun onPasswordChange(v: String) = _state.update { it.copy(password = v) }
    fun onPhoneChange(v: String) = _state.update { it.copy(phone = v.filter(Char::isDigit).take(10)) }
    fun onOtpChange(v: String) = _state.update { it.copy(otp = v.filter(Char::isDigit).take(6)) }

    fun sendOtp() {
        val phone = _state.value.phone
        if (phone.length < 10) {
            _state.update { it.copy(error = "Enter a valid 10-digit phone number") }
            return
        }
        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            delay(900)
            _state.update { it.copy(isLoading = false, otpSent = true) }
        }
    }

    fun submit() {
        val s = _state.value
        val error = when (s.mode) {
            AuthMode.LOGIN -> when {
                s.email.isBlank() -> "Email is required"
                !s.email.contains("@") -> "Enter a valid email"
                s.password.length < 6 -> "Password must be at least 6 characters"
                else -> null
            }
            AuthMode.REGISTER -> when {
                s.name.isBlank() -> "Name is required"
                s.email.isBlank() -> "Email is required"
                !s.email.contains("@") -> "Enter a valid email"
                s.password.length < 6 -> "Password must be at least 6 characters"
                else -> null
            }
            AuthMode.PHONE -> when {
                !s.otpSent -> "Please request an OTP first"
                s.otp.length < 6 -> "Enter the 6-digit OTP"
                else -> null
            }
        }

        if (error != null) {
            _state.update { it.copy(error = error) }
            return
        }

        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            delay(1200)
            _state.update { it.copy(isLoading = false, isAuthenticated = true) }
        }
    }

    fun signInWithGoogle() {
        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            delay(1000)
            _state.update { it.copy(isLoading = false, isAuthenticated = true) }
        }
    }
}