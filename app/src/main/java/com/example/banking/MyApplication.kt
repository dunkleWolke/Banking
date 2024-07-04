package com.example.banking;

import android.app.Application
import androidx.room.Room

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}

val appModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDB::class.java, "m-banking")
            .build()
            .accountDao()
    }
    single { AccountRepository(get()) }
    viewModel { AccountVM(get()) }
}