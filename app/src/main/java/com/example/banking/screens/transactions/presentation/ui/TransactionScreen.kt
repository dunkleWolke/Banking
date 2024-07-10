package com.example.banking.screens.transactions.presentation.ui


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.banking.database.model.Transaction
import com.example.banking.screens.transactions.presentation.viewmodel.TransactionViewModel
import com.example.banking.ui.theme.*
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel




@Composable
fun TransactionScreen(
    transactionViewModel: TransactionViewModel = koinViewModel(),
    transactionId: Int? = null,
    accountId: Int? = null,
    navController: NavController
) {

    val transactionState by transactionViewModel.transactions.collectAsState()

    var transactionName by remember { mutableStateOf("") }
    var transactionNumber by remember { mutableStateOf("") }
    var transactionStatus by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(transactionId) {
        if (transactionId != null) {
            coroutineScope.launch {
                val transaction: Transaction = transactionViewModel.getTransactionById(transactionId)
                transactionName = transaction.name
                transactionNumber = transaction.number
                transactionStatus = transaction.status
                amount = transaction.amount
                date = transaction.date
            }
        }
        else {
            val currentDate = java.time.LocalDate.now().toString()
            date = currentDate
        }
    }

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

        Field(
            label = "Transaction was applied in",
            value = transactionName,
            onValueChange = { transactionName = it },
            enabled = transactionId == null
        )

        Field(
            label = "Transaction Number",
            value = transactionNumber,
            onValueChange = { transactionNumber = it },
            enabled = transactionId == null
        )

        Field(
            label = "Transaction Status",
            value = transactionStatus,
            onValueChange = { transactionStatus = it },
            enabled = transactionId == null
        )

        Field(
            label = "Amount",
            value = amount,
            onValueChange = { amount = it },
            enabled = transactionId == null
        )

        if (transactionId != null) {
            Field(
                label = "Transaction Date",
                value = date,
                enabled = false,
                onValueChange = {date = it}
            )
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    if (accountId != null && transactionId == null) {
                        transactionViewModel.insertTransaction(
                            accountId = accountId,
                            name = transactionName,
                            number = transactionNumber,
                            status = transactionStatus,
                            amount = amount,
                            date = date
                        )
                    }
                    }
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