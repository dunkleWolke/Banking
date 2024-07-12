package com.example.banking.screens.transactions.presentation.ui


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import com.example.banking.screens.transactions.domain.model.TransactionDetail
import com.example.banking.screens.transactions.presentation.viewmodel.TransactionViewModel
import com.example.banking.ui.theme.*
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel



@SuppressLint("UnrememberedMutableState")
@Composable
fun TransactionScreen(
    transactionViewModel: TransactionViewModel = koinViewModel(),
    transactionId: Int? = null,
    accountId: Int? = null,
    navController: NavController
) {
    val uiState by transactionViewModel.uiState.collectAsState()

    LaunchedEffect(transactionId) {
        if (transactionId != null) {
            transactionViewModel.getTransactionById(transactionId)
        } else {
            val currentDate = java.time.LocalDate.now().toString()
            transactionViewModel.setInitialTransactionDetails(TransactionDetail(date = currentDate))
        }
    }

    when (val state = uiState) {
        is TransactionViewModel.TransactionUiState.Loading -> {
            CircularProgressIndicator()
        }

        is TransactionViewModel.TransactionUiState.Loaded -> {
            var name by remember { mutableStateOf(state.transactionDetail?.name.orEmpty()) }
            var number by remember { mutableStateOf(state.transactionDetail?.number.orEmpty()) }
            var status by remember { mutableStateOf(state.transactionDetail?.status.orEmpty()) }
            var amount by remember { mutableStateOf(state.transactionDetail?.amount.orEmpty()) }

            var nameError by remember { mutableStateOf(false) }
            var numberError by remember { mutableStateOf(false) }
            var statusError by remember { mutableStateOf(false) }
            var amountError by remember { mutableStateOf(false) }

            val isFormValid by derivedStateOf {
                !nameError && !numberError && !statusError && !amountError &&
                        name.isNotEmpty() && number.isNotEmpty() && status.isNotEmpty() && amount.isNotEmpty()
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Dark)
            ) {
                Text(
                    text = "Transaction",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Field(
                    label = "Transaction was applied in",
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = it.isEmpty()
                    },
                    enabled = transactionId == null
                )

                Field(
                    label = "Transaction Number",
                    value = number,
                    onValueChange = {
                        number = it
                        numberError = it.isEmpty()
                    },
                    enabled = transactionId == null
                )

                Field(
                    label = "Transaction Status",
                    value = status,
                    onValueChange = {
                        status = it
                        statusError = it.isEmpty()
                    },
                    enabled = transactionId == null
                )

                Field(
                    label = "Amount",
                    value = amount,
                    onValueChange = {
                        amount = it
                        amountError = it.isEmpty()
                    },
                    enabled = transactionId == null
                )

                if (transactionId != null) {
                    Field(
                        label = "Transaction Date",
                        value = state.transactionDetail?.date.orEmpty(),
                        enabled = false,
                        onValueChange = { /* No-op */ }
                    )
                }

                Button(
                    onClick = {
                        if (accountId != null && transactionId == null) {
                            nameError = name.isEmpty()
                            numberError = number.isEmpty()
                            statusError = status.isEmpty()
                            amountError = amount.isEmpty()

                            if (isFormValid) {
                                transactionViewModel.insertTransaction(
                                    accountId = accountId,
                                    name = name,
                                    number = number,
                                    status = status,
                                    amount = amount,
                                    date = state.transactionDetail?.date.orEmpty()
                                )
                            }
                        }
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp ,top = 16.dp),
                    colors = ButtonDefaults.buttonColors(Blue),
                    shape = RoundedCornerShape(9.dp),
                    enabled = isFormValid
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
    }
}