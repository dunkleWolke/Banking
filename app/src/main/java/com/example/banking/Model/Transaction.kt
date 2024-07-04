package com.example.banking.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["accountId"]
    )]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val accountId: Int,
    val name: String,
    val number: String,
    val date: String,
    val status: String,
    val amount: String
)