package com.example.banking.ui.theme

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.banking.Model.Account
import com.example.banking.Model.Transaction

class TestAccountVM : ViewModel() {
    val accounts = mutableStateListOf<Account>()
    val transactions = mutableStateListOf<Transaction>()

    init {
        val account1 = Account(id = 1 ,accountName = "Dummy Account", accountNumber = "123456789", cardNumber = "1111222233334444")
        val account2 = Account(id = 2,accountName = "Second Dummy", accountNumber = "987654321", cardNumber = "5555666677778888")
        accounts.addAll(listOf(account1, account2))

        val transaction1 = Transaction(accountId = 1, name = "Dummy Transaction 1", number = "12345", date = "2024-07-02", status = "Pending", amount = "50.00")
        val transaction4 = Transaction(accountId = 1, name = "Dummy Transaction 4", number = "12345", date = "2024-07-08", status = "Pending", amount = "50.00")
        val transaction3 = Transaction(accountId = 1, name = "Dummy Transaction 3", number = "12345", date = "2026-07-02", status = "Pending", amount = "50.00")
        val transaction5 = Transaction(accountId = 1, name = "Dummy Transaction 5", number = "12345", date = "2024-09-02", status = "Completed", amount = "50.00")
        val transaction6 = Transaction(accountId = 1, name = "Dummy Transaction 6", number = "12345", date = "2024-07-17", status = "Pending", amount = "50.00")
        val transaction7 = Transaction(accountId = 1, name = "Dummy Transaction 7", number = "12345", date = "2025-07-02", status = "Pending", amount = "50.00")
        val transaction2 = Transaction(accountId = 2, name = "Dummy Transaction 2", number = "67890", date = "2024-07-02", status = "Completed", amount = "100.00")
        transactions.addAll(listOf(transaction1, transaction2, transaction3, transaction4, transaction5, transaction6, transaction7))
    }
    fun selectAccount(account: Account) {
        //
    }
    fun insertTransaction(accountId: Int, name: String, number: String, date: String, status: String, amount: String) {
    //
    }
}