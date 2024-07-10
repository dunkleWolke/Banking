package com.example.banking.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.banking.database.dao.AccountDao
import com.example.banking.database.dao.TransactionDao
import com.example.banking.database.model.Account
import com.example.banking.database.model.Transaction


@Database(entities = [Account::class, Transaction::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao() : TransactionDao
}