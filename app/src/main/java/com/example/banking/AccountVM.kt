package com.example.banking

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountVM (application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDB::class.java, "app-database"
    ).build()

    private val accountDao = db.accountDao()

    var accounts = mutableStateListOf<Account>()
        private set

    var transactions = mutableStateListOf<Transaction>()
        private set

    init {
            viewModelScope.launch {
                val account1 = Account(name = "first", number = "123456789", cardNumber = "1111-2222-3333-4444")
                val account2 = Account(name = "second", number = "987654321", cardNumber = "5555-6666-7777-8888")
                val account3 = Account(name = "third", number = "543216789", cardNumber = "9999-0000-1111-2222")

                withContext(Dispatchers.IO) {
                    accountDao.insertAccounts(account1, account2, account3)

                    val accountId1 = accountDao.getAccountIdByName("first")
                    val accountId2 = accountDao.getAccountIdByName("second")
                    val accountId3 = accountDao.getAccountIdByName("third")

                    insertTransaction(accountId1, "Company1", "12345", "2024-07-02", "Declined", "100.00")
                    insertTransaction(accountId2, "Company2", "67890", "2024-07-02", "In progress", "150.00")
                    insertTransaction(accountId3, "Company3", "54321", "2024-07-02", "Executed", "200.00")
                }
            accounts.addAll(accountDao.getAllAccounts())
        }
    }

    fun selectAccount(account: Account) {
        viewModelScope.launch {
            transactions.clear()
            transactions.addAll(accountDao.getTransactionsByAccountId(account.id))
        }
    }

    fun insertTransaction(accountId: Int, name: String, number: String, date: String, status: String, amount: String) {
        val transaction = Transaction(accountId, name, number, date, status, amount)
        viewModelScope.launch {
            accountDao.insertTransaction(transaction)
            transactions.add(transaction)
        }
    }
}