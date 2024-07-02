package com.example.banking

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

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