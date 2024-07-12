package com.example.banking.screens.main.data.repository

import com.example.banking.database.dao.AccountDao
import com.example.banking.database.model.Account
import com.example.banking.screens.main.domain.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepositoryImpl(
    private val accountDao: AccountDao
) : AccountRepository {

    private var selectedAccount: Account? = null

    override suspend fun getAllAccounts(): List<Account> = withContext(Dispatchers.IO) {
         accountDao.getAllAccounts()
    }

    override suspend fun getAccountById(accountId: Int): Account = withContext(Dispatchers.IO){
        accountDao.getAccountById(accountId)
    }

    override suspend fun getCurrentAccount(): Account? = withContext(Dispatchers.IO){
        selectedAccount ?: accountDao.getAllAccounts().firstOrNull().also {
            selectedAccount = it
        }
    }

    override suspend fun setCurrentAccount(account: Account) = withContext(Dispatchers.IO){
        selectedAccount = account
    }

    override suspend fun insertAccounts(vararg accounts: Account)= withContext(Dispatchers.IO) {
        accountDao.insertAccounts(*accounts)
    }

    override suspend fun getAccountIdByName(accountName: String): Int?= withContext(Dispatchers.IO) {
        accountDao.getAccountIdByName(accountName)
    }

}