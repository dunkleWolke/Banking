package com.example.banking

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "transactions",
    foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["accountId"]
    )]
)
class Transaction{
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    var accountId: Int = 0
    var name: String = ""
    var number: String = ""
    var date: String = ""
    var status: String = ""
    var amount: String = ""

    constructor() {}

    constructor(accountId: Int, name: String, number: String, date: String, status: String, amount: String) {
        this.accountId = accountId
        this.name = name
        this.number = number
        this.date = date
        this.status = status
        this.amount = amount
    }
}