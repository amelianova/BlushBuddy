package com.example.blushbuddy.ui.screen.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blushbuddy.data.local.entity.TransactionEntity
import com.example.blushbuddy.data.repository.BlushBuddyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class TransactionListState(
    val transactions: List<TransactionEntity> = emptyList(),
    val isLoading: Boolean = true
)

data class AddTransactionState(
    val amount: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
) {
    val estimatedPoints: Int get() = amount.toLongOrNull()?.let { (it / 10000).toInt() } ?: 0
}

class TransactionViewModel(
    private val repository: BlushBuddyRepository,
    private val memberId: Int
) : ViewModel() {
    private val _listState = MutableStateFlow(TransactionListState())
    val listState: StateFlow<TransactionListState> = _listState

    private val _addState = MutableStateFlow(AddTransactionState())
    val addState: StateFlow<AddTransactionState> = _addState

    init {
        viewModelScope.launch {
            repository.getTransactions(memberId).collectLatest { list ->
                _listState.value = TransactionListState(transactions = list, isLoading = false)
            }
        }
    }

    fun onAmountChange(value: String) {
        if (value.all { it.isDigit() }) {
            _addState.value = _addState.value.copy(amount = value, errorMessage = null)
        }
    }

    fun onDescriptionChange(value: String) {
        _addState.value = _addState.value.copy(description = value)
    }

    fun submitTransaction() {
        val amount = _addState.value.amount.toLongOrNull()
        if (amount == null || amount <= 0) {
            _addState.value = _addState.value.copy(errorMessage = "Nominal pembelian tidak valid")
            return
        }
        viewModelScope.launch {
            _addState.value = _addState.value.copy(isLoading = true)
            repository.addTransaction(memberId, amount, _addState.value.description)
            _addState.value = _addState.value.copy(isLoading = false, isSuccess = true)
        }
    }
}
