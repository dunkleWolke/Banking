package com.example.banking.screens.allTransactions.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banking.database.model.Account
import com.example.banking.database.model.Transaction
import com.example.banking.screens.allTransactions.domain.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AllTransactionViewModel (
    private val transactionRepository: TransactionRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow<AllTransactionUiState>(AllTransactionUiState.Loading)
    val uiState: StateFlow<AllTransactionUiState> = _uiState.asStateFlow()

    fun loadTransactionsForCurrentAccount(accountId: Int) {
        viewModelScope.launch {
            val transactions = transactionRepository.getTransactionsByAccountId(accountId)
            val sortedTransactions = transactions.sortedByDescending { it.date }
            _uiState.emit(AllTransactionUiState.Loaded(sortedTransactions))
        }
    }
    fun loadFilteredTransactionsForCurrentAccount(accountId: Int, startDate: String, endDate: String) {
        viewModelScope.launch {
            val filteredTransactions = transactionRepository.getTransactionsByAccountIdFiltered(accountId, startDate, endDate)
            _uiState.emit(AllTransactionUiState.Loaded(filteredTransactions))
        }
    }

    sealed interface AllTransactionUiState{

        data object Loading: AllTransactionUiState

        data class Loaded(val transactions: List<Transaction>) : AllTransactionUiState
    }
}