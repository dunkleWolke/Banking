package com.example.banking.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.banking.database.model.Account

@Dao
interface AccountDao {

    @Query("SELECT COUNT(*) FROM accounts")
    fun getAccountCount(): Int

    @Query("DELETE FROM accounts")
    fun deleteAllAccounts()

    @Query("SELECT * FROM accounts WHERE id = :id")
    fun getAccountById(id: Int): Account

    @Query("SELECT * FROM accounts")
    fun getAllAccounts(): List<Account>

    @Query("SELECT id FROM accounts WHERE accountName = :accountName")
    fun getAccountIdByName(accountName: String):Int

    @Insert
    fun insertAccounts(vararg accounts: Account)

}