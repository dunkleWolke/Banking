package com.example.banking.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.banking.Model.Account
import com.example.banking.R

@Composable
fun AccountItem(account: Account, onClick: () -> Unit) {
    Column(   modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(16.dp)
        .background(Gray, shape = RoundedCornerShape(9.dp))
        .padding(16.dp) )
    {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.card),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
            ) {
                Text(text = account.accountName, fontWeight = FontWeight.Bold, color = White)
                Text(color = WhiteGray, text = "${account.accountNumber}")
                Text(
                    text = "${account.cardNumber.replaceRange(4, 12, "*".repeat(8))}",
                    color = WhiteGray,
                )
            }
        }
    }
}