package com.example.banking.ui.theme.TransactionScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.banking.AccountVM
import com.example.banking.ui.theme.Dark
import com.example.banking.ui.theme.TestAccountVM
import com.example.banking.ui.theme.TransactionItem
import com.example.banking.ui.theme.White
import kotlinx.coroutines.launch

@Preview
@Composable
fun ShowAllTransaction() {
    val dummyViewModel = TestAccountVM()
   // AllTransactionsScreen(dummyViewModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTransactionsScreen(navController: NavHostController, accountViewModel: AccountVM) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val accounts by remember { mutableStateOf(accountViewModel.accounts) }
    val transactions by remember { mutableStateOf(accountViewModel.transactions) }
    var isFilterSheetVisible by remember { mutableStateOf(false) }
    var selectedAccount by remember { mutableStateOf(accounts.firstOrNull()) }

    val filteredTransactions = transactions.filter { it.accountId == selectedAccount?.id }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            if (isFilterSheetVisible) {
                FilterByDateBottomSheet(onSubmit = {
                    isFilterSheetVisible = false
                    scope.launch {
                        scaffoldState.bottomSheetState.hide()
                    }
                })
            }
        },
        sheetPeekHeight = 0.dp,
        sheetContainerColor = Dark
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "All Transactions",
                                modifier = Modifier.align(Alignment.Center),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = White
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint =White)
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                                isFilterSheetVisible = true
                            }
                        }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Filter", tint = White)
                        }
                    },
                    colors= TopAppBarDefaults.topAppBarColors(containerColor = Dark,
                        titleContentColor = White,
                        navigationIconContentColor = White,
                        actionIconContentColor = White )
                )
            },
            content = {
                Column(modifier = Modifier.padding(it)) {
                    filteredTransactions.forEach { transaction ->
                        TransactionItem(transaction) {
                            navController.navigate("transaction_screen/${transaction.id}")
                        }
                    }
                }
            },
            contentColor = White,
            containerColor = Dark
        )
    }
}