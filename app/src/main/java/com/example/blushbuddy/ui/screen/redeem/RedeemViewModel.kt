package com.example.blushbuddy.ui.screen.redeem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blushbuddy.data.local.entity.MemberEntity
import com.example.blushbuddy.data.repository.BlushBuddyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class Reward(
    val id: Int,
    val name: String,
    val description: String,
    val pointsCost: Int,
    val emoji: String
)

val REWARDS = listOf(
    Reward(1, "Lip Balm", "Pelembab bibir mini BlushBuddy", 50, "💄"),
    Reward(2, "Sheet Mask", "Masker wajah 1 lembar", 100, "🧖"),
    Reward(3, "Travel Moisturizer", "Pelembab ukuran travel 15ml", 150, "🧴"),
    Reward(4, "Serum Sample", "Sample serum vitamin C 5ml", 200, "✨"),
    Reward(5, "Mini Skincare Kit", "Paket perawatan kulit lengkap", 300, "🎁")
)

data class RedeemUiState(
    val member: MemberEntity? = null,
    val isLoading: Boolean = true,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

class RedeemViewModel(
    private val repository: BlushBuddyRepository,
    private val memberId: Int
) : ViewModel() {
    private val _uiState = MutableStateFlow(RedeemUiState())
    val uiState: StateFlow<RedeemUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.getMemberById(memberId).collectLatest { member ->
                _uiState.value = _uiState.value.copy(member = member, isLoading = false)
            }
        }
    }

    fun redeem(reward: Reward) {
        val currentPoints = _uiState.value.member?.totalPoints ?: 0
        if (currentPoints < reward.pointsCost) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Poin tidak cukup. Butuh ${reward.pointsCost} poin, kamu punya $currentPoints poin."
            )
            return
        }
        viewModelScope.launch {
            repository.redeemReward(memberId, reward.pointsCost)
            _uiState.value = _uiState.value.copy(
                successMessage = "${reward.emoji} ${reward.name} berhasil diredeem!",
                errorMessage = null
            )
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(successMessage = null, errorMessage = null)
    }
}
