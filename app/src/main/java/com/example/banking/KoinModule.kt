package com.example.banking

import androidx.room.Room
import com.example.banking.DB.AccountRepository
import com.example.banking.DB.AppDB
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDB::class.java, "m-banking")
            .build()
            .accountDao()
    }
    single { AccountRepository(get()) }
    viewModel { AccountVM(get()) }
}