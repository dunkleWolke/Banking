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
        sheetPeekHeight = 450.dp,
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

@Composable
fun AccountInfoBlock(accountName: String, accountNumber: String, cardNumber: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
            .background(Gray, shape = RoundedCornerShape(9.dp))
            .padding(16.dp)

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.card),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = accountName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = White
                )
                Text(color = WhiteGray, text = "${accountNumber}")
                Text(text = "${cardNumber.replaceRange(4, 12, "*".repeat(8))}", color = WhiteGray,)
            }
        }
    }
}

@Composable
fun ViewAllBlock(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Recent Transactions", fontWeight = FontWeight.Bold, color = White, fontSize = 24.sp)
        Text(
            text = "VIEW ALL",
            color = Blue,
            modifier = Modifier.clickable { onClick() }
        )
    }
}

@Composable
fun RecentTransactionsBlock(transactions: List<Transaction>) {
    Column {
        transactions.forEach { transaction ->
            TransactionItem(transaction)
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    val textColor = when (transaction.status) {
        "Completed" -> Color.Green
        "Pending" -> Color.Yellow
        else -> Color.Red
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {

            Text(text = transaction.name, fontWeight = FontWeight.Bold, color = White)
            Text(text = transaction.date, color = WhiteGray)
            Text(text = transaction.status, color = textColor)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = transaction.amount, color = White)
        Icon(Icons.Filled.ArrowForward, contentDescription = "Next", tint = Color.White, modifier = Modifier
                .size(15.dp),)
    }

}

@Composable
fun SelectAccountBottomSheet(accounts: List<Account>, onAccountSelected: (Account) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Select The Account",  fontWeight = FontWeight.Bold, color = White, fontSize = 28.sp)
        Spacer(modifier = Modifier.height(8.dp))

        accounts.forEach { account ->
            AccountItem(
                account = account,
                onClick = { onAccountSelected(account) }
            )
        }
    }
}

@Composable
fun AccountItem(account: Account, onClick: () -> Unit) {
    Column(   modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(16.dp)
        .background(Gray, shape = RoundedCornerShape(9.dp))
        .padding(16.dp) )
    {
        Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.card),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
        ) {
            Text(text = account.accountName, fontWeight = FontWeight.Bold, color = White)
            Text(color = WhiteGray, text = "${account.accountNumber}")
            Text(
                text = "${account.cardNumber.replaceRange(4, 12, "*".repeat(8))}",
                color = WhiteGray,
            )
        }
    }
}
}


class TestAccountVM : ViewModel() {
    val accounts = mutableStateListOf<Account>()
    val transactions = mutableStateListOf<Transaction>()

    init {
        val account1 = Account(name = "Dummy Account", number = "123456789", cardNumber = "1111222233334444")
        val account2 = Account(name = "Second Dummy", number = "987654321", cardNumber = "5555666677778888")
        accounts.addAll(listOf(account1, account2))

        val transaction1 = Transaction(accountId = 1, name = "Dummy Transaction 1", number = "12345", date = "2024-07-02", status = "Pending", amount = "50.00")
        val transaction2 = Transaction(accountId = 2, name = "Dummy Transaction 2", number = "67890", date = "2024-07-02", status = "Completed", amount = "100.00")
        transactions.addAll(listOf(transaction1, transaction2))
    }
    fun selectAccount(account: Account) {

    }

    fun insertTransaction(accountId: Int, name: String, number: String, date: String, status: String, amount: String) {

    }
}