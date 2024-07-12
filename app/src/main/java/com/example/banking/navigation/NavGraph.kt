package com.example.banking.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.banking.screens.allTransactions.presentation.ui.AllTransactionsScreen

import com.example.banking.screens.main.presentation.ui.MainScreen
import com.example.banking.screens.transactions.presentation.ui.TransactionScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(startDestination: String = Screen.Home.route) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Home.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.Transactions.route,
            arguments = listOf(
                navArgument("transactionId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("accountId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getInt("transactionId")
            val accountId = backStackEntry.arguments?.getInt("accountId")
            val actualTransactionId = if (transactionId == -1) null else transactionId
            TransactionScreen(transactionId = actualTransactionId, accountId = accountId, navController = navController)
        }

        composable(
            route = Screen.AllTransaction.route,
            arguments = listOf(
                navArgument("accountId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val accountId = backStackEntry.arguments?.getInt("accountId")
            if (accountId != null) {
                AllTransactionsScreen(accountId = accountId, navController = navController)
            }
        }
    }
}