package com.example.banking.screens.main.data.repository

import com.example.banking.database.dao.AccountDao
import com.example.banking.database.model.Account
import com.example.banking.screens.main.domain.AccountRepository

class AccountRepositoryImpl(
    private val accountDao: AccountDao
) : AccountRepository {

    private var selectedAccount: Account? = null

    override suspend fun getAllAccounts(): List<Account> {
        return accountDao.getAllAccounts()
    }

    override suspend fun getAccountById(accountId: Int): Account {
        return accountDao.getAccountById(accountId)
    }

    override suspend fun getCurrentAccount(): Account? {
        return selectedAccount ?: accountDao.getAllAccounts().firstOrNull().also {
            selectedAccount = it
        }
    }

    override suspend fun setCurrentAccount(account: Account) {
        selectedAccount = account
    }

    override suspend fun insertAccounts(vararg accounts: Account) {
        accountDao.insertAccounts(*accounts)
    }

    override suspend fun getAccountIdByName(accountName: String): Int? {
        return accountDao.getAccountIdByName(accountName)
    }

}