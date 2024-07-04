package com.example.banking

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banking.DB.AccountRepository
import com.example.banking.Model.Account
import com.example.banking.Model.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountVM (private val repository: AccountRepository) : ViewModel() {

    var accounts = mutableStateListOf<Account>()
        private set

    var transactions = mutableStateListOf<Transaction>()
        private set

    init {
            viewModelScope.launch {
                val account1 = Account(accountName = "first", accountNumber = "123456789", cardNumber = "1111222233334444")
                val account2 = Account(accountName = "second", accountNumber = "987654321", cardNumber = "5555666677778888")
                val account3 = Account(accountName = "third", accountNumber = "543216789", cardNumber = "9999000011112222")

                withContext(Dispatchers.IO) {
                    repository.insertAccounts(account1, account2, account3)

                    val accountId1 = repository.getAccountIdByName("first")
                    val accountId2 = repository.getAccountIdByName("second")
                    val accountId3 = repository.getAccountIdByName("third")

                    insertTransaction(accountId1, "Company1", "12345", "2024-07-02", "Declined", "100.00")
                    insertTransaction(accountId2, "Company2", "67890", "2024-07-02", "In progress", "150.00")
                    insertTransaction(accountId3, "Company3", "54321", "2024-07-02", "Executed", "200.00")
                }
            accounts.addAll(repository.getAllAccounts())
        }
    }

    fun selectAccount(account: Account) {
        viewModelScope.launch {
            transactions.clear()
            transactions.addAll(repository.getTransactionsByAccountId(account.id))
        }
    }

    fun insertTransaction(accountId: Int, name: String, number: String, date: String, status: String, amount: String) {
        val transaction = Transaction(accountId = accountId, name = name, number = number, date = date,
            status = status, amount = amount)
        viewModelScope.launch {
            repository.insertTransaction(transaction)
            transactions.add(transaction)
        }
    }
}