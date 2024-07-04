package com.example.banking.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectAccountBottomSheet(accounts: List<Account>, onAccountSelected: (Account) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Select The Account",  fontWeight = FontWeight.Bold, color = White, fontSize = 28.sp)
        Spacer(modifier = Modifier.height(8.dp))

        accounts.forEach { account ->
            AccountItem(
                account = account,
                onClick = { onAccountSelected(account) }
            )
        }
    }
}
