package com.example.banking.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun RecentTransactionsBlock(transactions: List<Transaction>) {
    Column {
        transactions.forEach { transaction ->
            TransactionItem(transaction)
        }
    }
}