package com.example.banking.screens.main.data.repository

import com.example.banking.database.dao.AccountDao
import com.example.banking.database.dao.TransactionDao
import com.example.banking.database.model.Account
import com.example.banking.database.model.Transaction
import com.example.banking.screens.main.domain.AccountRepository
import com.example.banking.screens.main.domain.InitialDataRepository
import com.example.banking.screens.main.domain.TransactionRepository

class InitialDataRepositoryImpl(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val accountDao: AccountDao,
    private val transactionDao: TransactionDao
) : InitialDataRepository {

    override suspend fun clearDatabase() {
        transactionDao.deleteAllTransactions()
        accountDao.deleteAllAccounts()
    }

    override suspend fun initializeDatabaseWithInitialData() {
        if (isDatabaseEmpty()) {
            val account1 = Account(
                accountName = "first",
                accountNumber = "123456789",
                cardNumber = "1111222233334444"
            )
            val account2 = Account(
                accountName = "second",
                accountNumber = "987654321",
                cardNumber = "5555666677778888"
            )
            val account3 = Account(
                accountName = "third",
                accountNumber = "543216789",
                cardNumber = "9999000011112222"
            )

            accountRepository.insertAccounts(account1, account2, account3)

            val accountId1 = accountRepository.getAccountIdByName("first")
            val accountId2 = accountRepository.getAccountIdByName("second")
            val accountId3 = accountRepository.getAccountIdByName("third")

            val transaction1 =
                Transaction(1, accountId1!!, "Company1", "12345", "15/07/2024", "Pending", "100.00")
            val transaction2 = Transaction(
                2,
                accountId2!!,
                "Company2",
                "67890",
                "2024-07-02",
                "In progress",
                "150.00"
            )
            val transaction3 =
                Transaction(3, accountId3!!, "Company3", "54321", "02/07/2024", "Completed", "200.00")

            transactionRepository.insertTransaction(transaction1)
            transactionRepository.insertTransaction(transaction2)
            transactionRepository.insertTransaction(transaction3)
        }
    }


    private suspend fun isDatabaseEmpty(): Boolean {
        return accountDao.getAccountCount() == 0
    }
}