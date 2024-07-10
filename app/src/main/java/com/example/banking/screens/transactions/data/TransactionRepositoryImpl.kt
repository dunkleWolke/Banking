package com.example.banking.screens.transactions.data

import com.example.banking.database.dao.TransactionDao
import com.example.banking.database.model.Transaction
import com.example.banking.screens.transactions.domain.TransactionRepository

class TransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    TransactionRepository {
    override suspend fun getTransactionsByAccountId(accountId: Int): List<Transaction> {
        return transactionDao.getTransactionsByAccountId(accountId)
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    override suspend fun getTransactionById(transactionId: Int): Transaction{
       return transactionDao.getTransactionById(transactionId)
    }
}