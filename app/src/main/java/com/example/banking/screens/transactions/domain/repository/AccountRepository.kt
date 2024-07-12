package com.example.banking.screens.transactions.domain.repository

import com.example.banking.database.model.Account

interface AccountRepository {
    suspend fun getAccountById(accountId: Int): Account
}