package com.example.banking.database.koin

import androidx.room.Room

import com.example.banking.database.AppDB
import com.example.banking.database.dao.AccountDao
import com.example.banking.database.dao.TransactionDao
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {

    single<AppDB> {
        Room.databaseBuilder(androidContext(), AppDB::class.java, "m-banking")
            .build()
    }
    single<AccountDao> { get<AppDB>().accountDao() }
    single<TransactionDao> { get<AppDB>().transactionDao() }
}