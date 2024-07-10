package com.example.banking.screens.transactions.data

import com.example.banking.database.dao.AccountDao
import com.example.banking.database.model.Account
import com.example.banking.screens.transactions.domain.AccountRepository


class AccountRepositoryImpl(private val accountDao: AccountDao)
    : AccountRepository {

    override suspend fun getAccountById(accountId: Int): Account {
        return accountDao.getAccountById(accountId)
    }
}