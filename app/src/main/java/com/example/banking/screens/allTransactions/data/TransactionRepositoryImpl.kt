package com.example.banking.screens.allTransactions.data

import com.example.banking.database.dao.TransactionDao
import com.example.banking.database.model.Transaction
import com.example.banking.screens.allTransactions.domain.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale


class TransactionRepositoryImpl(private val transactionDao: TransactionDao) : TransactionRepository {

    override suspend fun getTransactionsByAccountId(accountId: Int): List<Transaction> = withContext(
        Dispatchers.IO){
        transactionDao.getTransactionsByAccountId(accountId)
    }

    override suspend fun getTransactionsByAccountIdFiltered(accountId: Int, startDate: String, endDate: String): List<Transaction> = withContext( Dispatchers.IO) {
        val transactions = transactionDao.getTransactionsByAccountId(accountId)
        val filteredTransactions = transactions.filter { transaction ->
            val transactionDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(transaction.date)
            val startDateParsed = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(startDate)
            val endDateParsed = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(endDate)

            transactionDate in startDateParsed..endDateParsed
        }
        filteredTransactions
    }

}