package com.example.banking.screens.main.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banking.database.model.Account
import com.example.banking.database.model.Transaction
import com.example.banking.screens.main.domain.AccountRepository
import com.example.banking.screens.main.domain.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val _selectedAccount = MutableStateFlow<Account?>(null)
    val selectedAccount: StateFlow<Account?> = _selectedAccount

    private var isInitialized = false

    init {
        viewModelScope.launch {
            loadInitialData()
        }
    }
    private suspend fun loadInitialData() {
        if (!isInitialized) {
            withContext(Dispatchers.IO) {
                val allAccounts = accountRepository.getAllAccounts()
                val currentAccount = accountRepository.getCurrentAccount()

                _accounts.value = allAccounts
                _selectedAccount.value = currentAccount
                currentAccount?.let {
                    _transactions.value = transactionRepository.getTransactionsByAccountId(it.id)
                }
            }
            isInitialized = true
        }
    }



    fun selectAccount(account: Account) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _selectedAccount.value = account
                accountRepository.setCurrentAccount(account)
                _transactions.value = transactionRepository.getTransactionsByAccountId(account.id)
            }
        }
    }

    fun loadTransactionsForCurrentAccount(account: Account) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _transactions.value = transactionRepository.getTransactionsByAccountId(account.id)
            }
        }
    }

}