package com.example.banking.screens.transactions.domain.repository

import com.example.banking.database.model.Transaction

interface TransactionRepository {

    suspend fun getTransactionsByAccountId(accountId: Int): List<Transaction>

    suspend fun insertTransaction(transaction: Transaction)

    suspend fun getTransactionById(transactionId:Int) : Transaction
}