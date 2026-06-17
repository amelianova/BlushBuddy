package com.example.blushbuddy.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blushbuddy.data.repository.BlushBuddyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMemberId: Int? = null
)

class LoginViewModel(private val repository: BlushBuddyRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(value: String) { _uiState.value = _uiState.value.copy(email = value) }

    fun login() {
        val email = _uiState.value.email.trim()
        if (email.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Email tidak boleh kosong")
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val member = repository.loginMember(email)
            if (member != null) {
                _uiState.value = _uiState.value.copy(isLoading = false, successMemberId = member.id)
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Akun tidak ditemukan. Silakan daftar terlebih dahulu."
                )
            }
        }
    }
}
