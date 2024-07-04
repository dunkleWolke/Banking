package com.example.banking

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Account::class, Transaction::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun accountDao(): AccountDao

    companion object{
        private var INSTANCE: AppDB? = null
        fun getDB(context: Context): AppDB {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "m-banking",
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}