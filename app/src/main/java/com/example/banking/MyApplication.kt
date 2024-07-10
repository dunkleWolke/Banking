package com.example.banking;

import android.app.Application

import com.example.banking.database.koin.databaseModule
import com.example.banking.screens.main.domain.InitialDataRepository
import com.example.banking.screens.main.koin.mainModule
import com.example.banking.screens.transactions.koin.transactionModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(databaseModule, mainModule, transactionModule)
        }
        val initialDataRepository: InitialDataRepository by inject()
        CoroutineScope(Dispatchers.IO).launch {
            initialDataRepository.clearDatabase()
            initialDataRepository.initializeDatabaseWithInitialData()
        }

    }
}

