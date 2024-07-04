package com.example.banking.ui.theme

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class TestAccountVM : ViewModel() {
    val accounts = mutableStateListOf<Account>()
    val transactions = mutableStateListOf<Transaction>()

    init {
        val account1 = Account(name = "Dummy Account", number = "123456789", cardNumber = "1111222233334444")
        val account2 = Account(name = "Second Dummy", number = "987654321", cardNumber = "5555666677778888")
        accounts.addAll(listOf(account1, account2))

        val transaction1 = Transaction(accountId = 1, name = "Dummy Transaction 1", number = "12345", date = "2024-07-02", status = "Pending", amount = "50.00")
        val transaction2 = Transaction(accountId = 2, name = "Dummy Transaction 2", number = "67890", date = "2024-07-02", status = "Completed", amount = "100.00")
        transactions.addAll(listOf(transaction1, transaction2))
    }
    fun selectAccount(account: Account) {
        //
    }
    fun insertTransaction(accountId: Int, name: String, number: String, date: String, status: String, amount: String) {
    //
    }
}