package com.example.banking.screens.transactions.koin


import com.example.banking.screens.transactions.data.TransactionRepositoryImpl
import com.example.banking.screens.transactions.domain.repository.AccountRepository
import com.example.banking.screens.transactions.domain.repository.TransactionRepository
import com.example.banking.screens.transactions.data.AccountRepositoryImpl
import com.example.banking.screens.transactions.presentation.viewmodel.TransactionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transactionModule = module {

    viewModel<TransactionViewModel> {
        TransactionViewModel(transactionRepository = get(), accountRepository = get())
    }

    single<TransactionRepository> {
        TransactionRepositoryImpl(transactionDao = get())
    }
    single<AccountRepository> {
        AccountRepositoryImpl( accountDao = get())
    }

}