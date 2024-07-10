package com.example.banking.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "accounts")
data class Account (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val accountName: String,
    val accountNumber: String,
    val cardNumber: String
)
