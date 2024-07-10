package com.example.banking.screens.main.data.repository

import com.example.banking.database.dao.TransactionDao
import com.example.banking.database.model.Transaction
import com.example.banking.screens.main.domain.TransactionRepository

class TransactionsRepositoryImpl(
    private val transactionDao: TransactionDao
): TransactionRepository {

    override suspend fun getTransactionsByAccountId(accountId: Int): List<Transaction> {
        return transactionDao.getTransactionsByAccountId(accountId)
    }

    override suspend fun getAllTransactions(): List<Transaction> {
        return transactionDao.getAllTransactions()
    }


    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }
}