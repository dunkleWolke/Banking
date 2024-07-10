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
import androidx.navigation.NavHostController
import com.example.banking.AccountVM
import com.example.banking.Model.Account
import com.example.banking.R

import com.example.banking.Model.Transaction
import com.example.banking.ui.theme.TransactionScreen.FilterByDateBottomSheet
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainActivity(){

}

@ExperimentalMaterial3Api
@Composable
fun MainScreen(navController: NavHostController, accountViewModel: AccountVM = koinViewModel()) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        accountViewModel.loadInitialData()
    }

    val accounts by remember { mutableStateOf(accountViewModel.accounts) }
    val transactions by remember { mutableStateOf(accountViewModel.transactions) }
    var selectedAccount by remember { mutableStateOf(accounts.firstOrNull()) }

    val filteredTransactions = transactions.filter { it.accountId == selectedAccount?.id }

    var isFilterSheetVisible by remember { mutableStateOf(false) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            if (isFilterSheetVisible) {
                FilterByDateBottomSheet(onSubmit = {
                    scope.launch {
                        scaffoldState.bottomSheetState.hide()
                        isFilterSheetVisible = false
                    }
                })
            } else {
            SelectAccountBottomSheet(
                accounts = accounts,
                onAccountSelected = { account ->
                    selectedAccount = account
                    accountViewModel.selectAccount(account)
                    scope.launch {
                        scaffoldState.bottomSheetState.hide()
                    }
                }
            )
        }},
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
                            isFilterSheetVisible = false
                        }
                    }
                )
            }

            ViewAllBlock(onClick = {
                navController.navigate("all_transactions")
            })

            RecentTransactionsBlock(transactions = filteredTransactions) { transaction ->
                navController.navigate("transaction_screen/${transaction.id}")
            }

            Button(
                onClick = {navController.navigate("transaction_screen") },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(Blue),
                shape = RoundedCornerShape(60.dp)
            ) {
                Text(
                    text = "+",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

