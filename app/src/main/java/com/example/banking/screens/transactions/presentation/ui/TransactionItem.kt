package com.example.banking.screens.transactions.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.banking.database.model.Transaction
import com.example.banking.ui.theme.Gray
import com.example.banking.ui.theme.White
import com.example.banking.ui.theme.WhiteGray

@Composable
fun TransactionItem(transaction: Transaction, onTransactionClick: (Transaction) -> Unit) {
    val textColor = when (transaction.status) {
        "Completed" -> Color.Green
        "Pending" -> Color.Yellow
        else -> Color.Red
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Gray, shape = RoundedCornerShape(9.dp))
            .clickable { onTransactionClick(transaction) }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {

            Text(text = transaction.name, fontWeight = FontWeight.Bold, color = White)
            Text(text = transaction.date, color = WhiteGray)
            Text(text = transaction.status, color = textColor)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = transaction.amount, color = White)
        Icon(
            Icons.Filled.ArrowForward, contentDescription = "Next", tint = Color.White, modifier = Modifier
            .size(15.dp),)
    }

}