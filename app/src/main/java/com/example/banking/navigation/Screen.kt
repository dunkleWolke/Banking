package com.example.banking.navigation
const val id = ""

sealed class Screen(val route: String) {

    data object Home : Screen(route = "home_screen")

    data object Transactions : Screen("transaction_screen?transactionId={transactionId}&accountId={accountId}") {
        fun createRoute(transactionId: Int? = null, accountId: Int): String {
            val transactionIdPath = transactionId?.toString() ?: "-1"
            return "transaction_screen?transactionId=$transactionIdPath&accountId=$accountId"
        }
    }

}