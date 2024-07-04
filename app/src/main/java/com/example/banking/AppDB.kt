package com.example.banking

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Account::class, Transaction::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun accountDao(): AccountDao

}