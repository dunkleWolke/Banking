package com.example.banking.screens.transactions.data

import com.example.banking.database.dao.TransactionDao
import com.example.banking.database.model.Transaction
import com.example.banking.screens.transactions.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    TransactionRepository {
    override suspend fun getTransactionsByAccountId(accountId: Int): List<Transaction> = withContext(Dispatchers.IO){
       transactionDao.getTransactionsByAccountId(accountId)
    }

    override suspend fun insertTransaction(transaction: Transaction) = withContext(Dispatchers.IO) {
        transactionDao.insertTransaction(transaction)
    }

    override suspend fun getTransactionById(transactionId: Int): Transaction = withContext(Dispatchers.IO){
       transactionDao.getTransactionById(transactionId)
    }
}