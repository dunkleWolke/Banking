package com.example.banking.ui.theme.TransactionScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.banking.AccountVM
import com.example.banking.Model.Transaction
import com.example.banking.ui.theme.TestAccountVM
import com.example.banking.ui.theme.*
import org.koin.androidx.compose.koinViewModel


@Composable
fun TransactionScreen(navController: NavHostController, viewModel: AccountVM = koinViewModel(), transaction: Transaction? = null) {
    val transactionName by remember { mutableStateOf(transaction?.name ?: "") }
    val transactionNumber by remember { mutableStateOf(transaction?.number ?: "") }
    val transactionStatus by remember { mutableStateOf(transaction?.status ?: "") }
    val amount by remember { mutableStateOf(transaction?.amount ?: "") }
    val date by remember { mutableStateOf(transaction?.date ?: "") }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Transaction",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Field(label = "Transaction was applied in", value = transactionName, enabled = transaction == null)
        Field(label = "Transaction Number", value = transactionNumber, enabled = transaction == null)
        Field(label = "Transaction Status", value = transactionStatus, enabled = transaction == null)
        Field(label = "Amount", value = amount, enabled = transaction == null)

        if (transaction != null) {
            Field(label = "Transaction Date", value = date, enabled = false)
        }

        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(Blue),
            shape = RoundedCornerShape(9.dp)
        ) {
            Text(
                text = "Okay",
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

