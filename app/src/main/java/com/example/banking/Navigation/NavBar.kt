package com.example.banking.Navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.banking.AccountVM
import com.example.banking.ui.theme.MainScreen
import com.example.banking.ui.theme.TransactionScreen.AllTransactionsScreen
import com.example.banking.ui.theme.TransactionScreen.TransactionScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(navController: NavHostController, accountViewModel: AccountVM) {

    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(navController = navController, accountViewModel = accountViewModel)
        }
        composable("all_transactions") {
            AllTransactionsScreen(navController = navController, accountViewModel = accountViewModel)
        }
        composable(
            "transaction_screen/{transactionId}",
            arguments = listOf(navArgument("transactionId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val transactionId = navBackStackEntry.arguments?.getInt("transactionId")
            val transaction = transactionId?.let {
                accountViewModel.transactions.firstOrNull { transaction -> transaction.id == it }
            }
            TransactionScreen(navController = navController, viewModel = accountViewModel, transaction = transaction)
        }
        composable(
            "transaction_screen"
        ) {
            TransactionScreen(navController = navController, viewModel = accountViewModel, transaction = null)
        }
    }
}