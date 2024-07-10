package com.example.banking.screens.transactions.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banking.database.model.Account
import com.example.banking.database.model.Transaction
import com.example.banking.screens.transactions.domain.AccountRepository
import com.example.banking.screens.transactions.domain.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val _transactionId = MutableStateFlow<Int?>(null)
    val transactionId: StateFlow<Int?> = _transactionId

    fun setTransactionId(id: Int?) {
        _transactionId.value = id
    }


    fun loadTransactionsForCurrentAccount(currentAccount: Account) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _transactions.value = transactionRepository.getTransactionsByAccountId(currentAccount.id)
            }
        }
    }

    fun insertTransaction(accountId: Int, name: String, number: String, date: String, status: String, amount: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
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
    }

    suspend fun getTransactionById(id: Int): Transaction =
            withContext(Dispatchers.IO) {
                transactionRepository.getTransactionById(id)
            }

}