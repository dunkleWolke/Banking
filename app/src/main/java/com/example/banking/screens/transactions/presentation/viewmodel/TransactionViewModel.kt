package com.example.banking.screens.transactions.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banking.database.model.Account
import com.example.banking.database.model.Transaction
import com.example.banking.screens.transactions.domain.model.TransactionDetail
import com.example.banking.screens.transactions.domain.repository.AccountRepository
import com.example.banking.screens.transactions.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TransactionUiState>(TransactionUiState.Loading)
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    private fun loadTransactionsForCurrentAccount(currentAccount: Account) {
        viewModelScope.launch {
            val transactions = transactionRepository.getTransactionsByAccountId(currentAccount.id)
            _uiState.emit(TransactionUiState.Loaded(transactions, null))
        }
    }

    fun setInitialTransactionDetails(transactionDetail: TransactionDetail) {
        viewModelScope.launch {
            _uiState.emit(TransactionUiState.Loaded(emptyList(), transactionDetail))
        }
    }

    fun insertTransaction(accountId: Int, name: String, number: String, date: String, status: String, amount: String) {
        viewModelScope.launch {
                transactionRepository.insertTransaction(
                    Transaction(
                        accountId = accountId,
                        name = name,
                        number = number,
                        date = date,
                        status = status,
                        amount = amount
                    )
                )
            loadTransactionsForCurrentAccount(accountRepository.getAccountById(accountId))
            }
    }

    suspend fun getTransactionById(id: Int) {
        val transaction = transactionRepository.getTransactionById(id)
        val transactionDetail = TransactionDetail(
            name = transaction.name,
            number = transaction.number,
            status = transaction.status,
            amount = transaction.amount,
            date = transaction.date
        )
        _uiState.emit(TransactionUiState.Loaded(emptyList(), transactionDetail))
    }

    sealed interface TransactionUiState {
        data object Loading : TransactionUiState
        data class Loaded(val transactions: List<Transaction>, val transactionDetail: TransactionDetail?) : TransactionUiState
    }

}