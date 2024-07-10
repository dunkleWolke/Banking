package com.example.banking.screens.main.presentation.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.banking.navigation.Screen
import com.example.banking.screens.main.presentation.viewmodel.MainViewModel
import com.example.banking.ui.theme.Blue
import com.example.banking.ui.theme.Dark

import com.example.banking.screens.transactions.presentation.ui.FilterByDateBottomSheet
import com.example.banking.ui.theme.White
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@ExperimentalMaterial3Api
@Composable
fun MainScreen(accountViewModel: MainViewModel = koinViewModel(), navController: NavController) {
    var scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    val accounts by accountViewModel.accounts.collectAsState()
    val transactions by accountViewModel.transactions.collectAsState()
    val selectedAccount by accountViewModel.selectedAccount.collectAsState()

    val filteredTransactions = transactions.filter { it.accountId == selectedAccount?.id }
    var isFilterSheetVisible by remember { mutableStateOf(false) }

    LaunchedEffect(selectedAccount) {
        selectedAccount?.let { account ->
            accountViewModel.loadTransactionsForCurrentAccount(account)
        }
    }

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
                accounts = accounts.toList(),
                onAccountSelected = { account ->
                    accountViewModel.selectAccount(account)

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

            })
            RecentTransactionsBlock(transactions = filteredTransactions,
                onTransactionClick = { transaction ->
                    navController.navigate(Screen.Transactions.createRoute(transaction.id, transaction.accountId))
                })

            Button(
                onClick = { selectedAccount?.let { account ->
                    navController.navigate(Screen.Transactions.createRoute(accountId = account.id))
                }},
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

