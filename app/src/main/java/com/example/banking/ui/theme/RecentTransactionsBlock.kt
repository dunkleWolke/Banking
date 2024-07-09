package com.example.banking.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.banking.Model.Transaction

@Composable
fun RecentTransactionsBlock(transactions: List<Transaction>) {
    val sortedTransactions = transactions.sortedByDescending { it.date }
    val recentTransactions = sortedTransactions.take(5)

    Column {
       recentTransactions.forEach { transaction ->
            TransactionItem(transaction)
        }
    }
}