package com.example.banking

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "accounts")
class Account {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    var accountName: String = ""
    var accountNumber: String = ""
    var cardNumber: String = ""


    constructor() {}

    constructor(name: String, number: String, cardNumber: String) {
        this.accountName = name
        this.accountNumber = number
        this.cardNumber = cardNumber
    }
}