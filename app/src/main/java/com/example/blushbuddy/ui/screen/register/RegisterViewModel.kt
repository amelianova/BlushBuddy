package com.example.blushbuddy.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blushbuddy.data.repository.BlushBuddyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMemberId: Int? = null
)

class RegisterViewModel(private val repository: BlushBuddyRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onNameChange(value: String) { _uiState.value = _uiState.value.copy(name = value) }
    fun onEmailChange(value: String) { _uiState.value = _uiState.value.copy(email = value) }
    fun onPhoneChange(value: String) { _uiState.value = _uiState.value.copy(phone = value) }

    fun register() {
        val state = _uiState.value
        if (state.name.isBlank() || state.email.isBlank() || state.phone.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Semua field harus diisi")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            _uiState.value = state.copy(errorMessage = "Format email tidak valid")
            return
        }
        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, errorMessage = null)
            val id = repository.registerMember(state.name, state.email, state.phone)
            _uiState.value = _uiState.value.copy(isLoading = false, successMemberId = id.toInt())
        }
    }
}
