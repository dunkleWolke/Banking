package com.example.banking.screens.main.koin

import com.example.banking.screens.main.data.repository.AccountRepositoryImpl
import com.example.banking.screens.main.data.repository.InitialDataRepositoryImpl
import com.example.banking.screens.main.data.repository.TransactionsRepositoryImpl
import com.example.banking.screens.main.domain.AccountRepository
import com.example.banking.screens.main.domain.InitialDataRepository
import com.example.banking.screens.main.domain.TransactionRepository
import com.example.banking.screens.main.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel<MainViewModel> {
        MainViewModel(accountRepository = get(), transactionRepository = get())
    }

    single<AccountRepository> {
        AccountRepositoryImpl(accountDao = get())
    }
    single<TransactionRepository> {
        TransactionsRepositoryImpl(transactionDao = get())
    }
    single<InitialDataRepository> {
        InitialDataRepositoryImpl(get(), get(), get(), get())
    }
}