package com.example.banking

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Account::class, Transaction::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun accountDao(): AccountDao
}