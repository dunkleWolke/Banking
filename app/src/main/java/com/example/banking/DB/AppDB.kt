package com.example.banking.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.banking.Model.Account
import com.example.banking.Model.Transaction


@Database(entities = [Account::class, Transaction::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun accountDao(): AccountDao

}