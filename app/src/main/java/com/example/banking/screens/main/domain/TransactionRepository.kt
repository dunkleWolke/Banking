package com.example.banking.screens.main.domain

import com.example.banking.database.model.Transaction

interface TransactionRepository {
 suspend fun getTransactionsByAccountId(accountId: Int): List<Transaction>
 suspend fun getAllTransactions(): List<Transaction>
 suspend fun insertTransaction(transaction: Transaction)
}