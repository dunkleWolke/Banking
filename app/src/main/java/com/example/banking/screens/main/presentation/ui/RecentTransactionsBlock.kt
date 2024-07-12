package com.example.banking.screens.main.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.banking.database.model.Transaction
import com.example.banking.screens.transactions.presentation.ui.TransactionItem

@Composable
fun RecentTransactionsBlock(transactions: List<Transaction>?, onTransactionClick: (Transaction) -> Unit) {
    val sortedTransactions = transactions?.sortedByDescending { it.date }
    val recentTransactions = sortedTransactions?.take(5)

    Column {
        if (recentTransactions != null) {
            recentTransactions.forEach { transaction ->
                TransactionItem(transaction, onTransactionClick)
            }
        }
    }
}