package com.example.banking.screens.transactions.data

import com.example.banking.database.dao.AccountDao
import com.example.banking.database.model.Account
import com.example.banking.screens.transactions.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AccountRepositoryImpl(private val accountDao: AccountDao)
    : AccountRepository {

    override suspend fun getAccountById(accountId: Int): Account = withContext(Dispatchers.IO) {
       accountDao.getAccountById(accountId)
    }
}