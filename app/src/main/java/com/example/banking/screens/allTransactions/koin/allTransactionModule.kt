package com.example.banking.screens.allTransactions.koin

import com.example.banking.screens.allTransactions.domain.TransactionRepository
import com.example.banking.screens.allTransactions.presentation.viewmodel.AllTransactionViewModel
import com.example.banking.screens.allTransactions.data.TransactionRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val allTransactionModule = module {

    viewModel<AllTransactionViewModel> {
        AllTransactionViewModel(transactionRepository = get())
    }

    single<TransactionRepository> {
        TransactionRepositoryImpl(transactionDao = get())
    }

}
