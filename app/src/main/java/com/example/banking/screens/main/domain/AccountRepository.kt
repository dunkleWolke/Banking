package com.example.banking.screens.main.domain

import com.example.banking.database.model.Account

interface AccountRepository {

    suspend fun getAllAccounts(): List<Account>
    suspend fun getAccountById(accountId: Int): Account?
    suspend fun getCurrentAccount(): Account?
    suspend fun setCurrentAccount(account: Account)
    suspend fun insertAccounts(vararg accounts: Account)
    suspend fun getAccountIdByName(accountName: String): Int?
}