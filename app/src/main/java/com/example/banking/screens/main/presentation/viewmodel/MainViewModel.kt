package com.example.banking.screens.main.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banking.database.model.Account
import com.example.banking.database.model.Transaction
import com.example.banking.screens.main.domain.AccountRepository
import com.example.banking.screens.main.domain.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var isInitialized = false

    init {
        viewModelScope.launch {
            loadInitialData()
        }
    }
    private suspend fun loadInitialData() {
        if (!isInitialized) {

                val allAccounts = accountRepository.getAllAccounts()
                val currentAccount = accountRepository.getCurrentAccount()
                val transactions = currentAccount?.let{
                    transactionRepository.getTransactionsByAccountId(it.id)
                }
                _uiState.emit(MainUiState.Loaded(accounts = allAccounts, transaction = transactions, selectedAccount = currentAccount))

            isInitialized = true
        }
    }

    fun selectAccount(account: Account) {
        viewModelScope.launch {
            viewModelScope.launch {
                val transactions = transactionRepository.getTransactionsByAccountId(account.id)
                val accounts = accountRepository.getAllAccounts()
                _uiState.emit(MainUiState.Loaded(accounts = accounts, transaction = transactions, selectedAccount = account))
            }
        }
    }

    fun loadTransactionsForCurrentAccount(account: Account) {
        viewModelScope.launch {
            val transactions = transactionRepository.getTransactionsByAccountId(account.id)
            val currentState = _uiState.value
            if (currentState is MainUiState.Loaded) {
                _uiState.emit(currentState.copy(transaction = transactions))
            }
        }
    }

    sealed interface MainUiState{

        data object Loading: MainUiState

        data class Loaded(val accounts: List<Account>, val transaction: List<Transaction>?, val selectedAccount: Account?): MainUiState
    }

}


