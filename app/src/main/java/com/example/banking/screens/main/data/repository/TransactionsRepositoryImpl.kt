package com.example.banking.screens.main.data.repository

import com.example.banking.database.dao.TransactionDao
import com.example.banking.database.model.Transaction
import com.example.banking.screens.main.domain.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionsRepositoryImpl(
    private val transactionDao: TransactionDao
): TransactionRepository {

    override suspend fun getTransactionsByAccountId(accountId: Int): List<Transaction> = withContext(
        Dispatchers.IO) {
        transactionDao.getTransactionsByAccountId(accountId)
    }

    override suspend fun getAllTransactions(): List<Transaction> = withContext(Dispatchers.IO) {
        transactionDao.getAllTransactions()
    }


    override suspend fun insertTransaction(transaction: Transaction) = withContext(Dispatchers.IO){
        transactionDao.insertTransaction(transaction)
    }
}