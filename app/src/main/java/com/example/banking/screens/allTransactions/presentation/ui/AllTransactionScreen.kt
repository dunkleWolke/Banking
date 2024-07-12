package com.example.banking.screens.allTransactions.presentation.ui

import android.annotation.SuppressLint

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.banking.navigation.Screen
import com.example.banking.screens.allTransactions.presentation.viewmodel.AllTransactionViewModel
import com.example.banking.screens.allTransactions.presentation.viewmodel.CalendarViewModel
import com.example.banking.screens.transactions.presentation.ui.TransactionItem
import com.example.banking.ui.theme.Dark
import com.example.banking.ui.theme.White
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTransactionsScreen(
    accountId: Int,
    navController: NavController,
    viewModel: AllTransactionViewModel = koinViewModel(),
    calendarViewModel: CalendarViewModel = viewModel()
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    val uiState by viewModel.uiState.collectAsState()
    var isFilterSheetVisible by remember { mutableStateOf(false) }

    LaunchedEffect(accountId) {
        viewModel.loadTransactionsForCurrentAccount(accountId)
        calendarViewModel.reset()
    }
    when (val state = uiState) {
        is AllTransactionViewModel.AllTransactionUiState.Loading -> {
            CircularProgressIndicator()
        }

        is AllTransactionViewModel.AllTransactionUiState.Loaded -> {
            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetContent = {
                    if (isFilterSheetVisible) {
                        FilterByDateBottomSheet(viewModel = calendarViewModel,
                            onSubmit = { startDate, endDate ->
                                viewModel.loadFilteredTransactionsForCurrentAccount(accountId, startDate, endDate)
                                isFilterSheetVisible = false
                            }
                        )
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
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back",
                                        tint = White
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = {
                                    scope.launch {
                                        scaffoldState.bottomSheetState.expand()
                                        isFilterSheetVisible = true
                                    }
                                }) {
                                    Icon(
                                        Icons.Default.MoreVert,
                                        contentDescription = "Filter",
                                        tint = White
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Dark,
                                titleContentColor = White,
                                navigationIconContentColor = White,
                                actionIconContentColor = White
                            )
                        )
                    },
                    content = { paddingValues ->
                        Column(modifier = Modifier.padding(paddingValues)) {
                            state.transactions.forEach { transaction ->
                                TransactionItem(transaction = transaction) {
                                    navController.navigate(
                                        Screen.Transactions.setParams(
                                            transaction.id,
                                            transaction.accountId
                                        )
                                    )
                                }
                            }
                        }
                    },
                    containerColor = Dark
                )
            }
        }
    }
}