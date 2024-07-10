package com.example.banking.screens.main.domain

interface InitialDataRepository {
    suspend fun initializeDatabaseWithInitialData()
    suspend fun clearDatabase()
}