package com.example.banking.screens.main.presentation.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.banking.navigation.Screen
import com.example.banking.screens.main.presentation.viewmodel.MainViewModel
import com.example.banking.ui.theme.Blue
import com.example.banking.ui.theme.Dark

import com.example.banking.ui.theme.White
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@ExperimentalMaterial3Api
@Composable
fun MainScreen(accountViewModel: MainViewModel = koinViewModel(), navController: NavController) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    val uiState by accountViewModel.uiState.collectAsState()

    when (val state = uiState) {
        is MainViewModel.MainUiState.Loading -> {
            CircularProgressIndicator()
        }
        is MainViewModel.MainUiState.Loaded -> {

            var isFilterSheetVisible by remember { mutableStateOf(false) }

            LaunchedEffect(state.selectedAccount) {
                state.selectedAccount?.let { account ->
                    accountViewModel.loadTransactionsForCurrentAccount(account)
                }
            }

            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetContent = {
                    SelectAccountBottomSheet(
                        accounts = state.accounts.toList(),
                        onAccountSelected = { account ->
                            accountViewModel.selectAccount(account)

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
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    state.selectedAccount?.let { account ->
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
                        state.selectedAccount?.let { account ->
                            navController.navigate(Screen.AllTransaction.setParams(account.id))
                        }
                    })
                    RecentTransactionsBlock(
                        transactions = state.transaction,
                        onTransactionClick = { transaction ->
                            navController.navigate(
                                Screen.Transactions.setParams(
                                    transaction.id,
                                    transaction.accountId
                                )
                            )
                        })

                    Button(
                        onClick = {
                            state.selectedAccount?.let { account ->
                                navController.navigate(Screen.Transactions.setParams(accountId = account.id))
                            }
                        },
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
    }
}

