package com.example.banking.ui.theme

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.ViewModel
import com.example.banking.Account
import com.example.banking.R

import com.example.banking.Transaction
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainActivity(){
    val dummyViewModel = TestAccountVM()
    MainScreen(dummyViewModel)
}

@ExperimentalMaterial3Api
@Composable
fun MainScreen(accountViewModel: TestAccountVM) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val accounts by remember { mutableStateOf(accountViewModel.accounts.toTypedArray()) }
    val transactions by remember { mutableStateOf(accountViewModel.transactions.toTypedArray()) }
    var selectedAccount by remember { mutableStateOf(accounts.firstOrNull()) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            SelectAccountBottomSheet(
                accounts = accounts.toList(),
                onAccountSelected = { account ->
                    selectedAccount = account
                    accountViewModel.selectAccount(account)
                    scope.launch {
                        scaffoldState.bottomSheetState.hide()
                    }
                }
            )
        },
        sheetPeekHeight = 0.dp,
        sheetContainerColor = Dark
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Dark)
                .padding(16.dp)
        ) {
            Text(
                text = "Account",
                color = White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding( top = 16.dp)
            )

            selectedAccount?.let { account ->
                AccountInfoBlock(
                    accountName = account.accountName,
                    accountNumber = account.accountNumber,
                    cardNumber = account.cardNumber,
                    onClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
                )
            }

            ViewAllBlock(onClick = {})
            RecentTransactionsBlock(transactions = transactions.toList())
        }
    }
}

