package com.example.banking.screens.allTransactions.domain

import com.example.banking.database.model.Transaction

interface TransactionRepository {

    suspend fun getTransactionsByAccountId(accountId: Int): List<Transaction>

    suspend fun getTransactionsByAccountIdFiltered(accountId: Int, startDate: String, endDate: String): List<Transaction>
}