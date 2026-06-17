package com.example.blushbuddy.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blushbuddy.data.local.entity.MemberEntity
import com.example.blushbuddy.data.repository.BlushBuddyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class HomeUiState(
    val member: MemberEntity? = null,
    val isLoading: Boolean = true
)

class HomeViewModel(
    private val repository: BlushBuddyRepository,
    private val memberId: Int
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.getMemberById(memberId).collectLatest { member ->
                _uiState.value = HomeUiState(member = member, isLoading = false)
            }
        }
    }

    fun getMemberStatus(points: Int): String = when {
        points >= 500000 -> "Black Member"
        points >= 1000 -> "Gold Member"
        points >= 500 -> "Silver Member"
        else -> "Bronze Member"
    }
}
