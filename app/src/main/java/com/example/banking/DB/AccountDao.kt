package com.example.banking.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.banking.Model.Account
import com.example.banking.Model.Transaction

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts")
    fun getAllAccounts(): List<Account>

    @Query("SELECT id FROM accounts WHERE accountName = :accountName")
    fun getAccountIdByName(accountName: String):Int

    @Insert
    fun insertAccounts(vararg accounts: Account)

    @Query("SELECT * FROM transactions WHERE accountId = :accountId")
    fun getTransactionsByAccountId(accountId: Int): List<Transaction>

    @Insert
    fun insertTransaction(vararg transaction: Transaction)


}