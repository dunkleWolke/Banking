package com.example.banking.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.banking.database.model.Transaction


@Dao
interface TransactionDao {

    @Query("DELETE FROM transactions")
    fun deleteAllTransactions()

    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): List<Transaction>

    @Query("SELECT * FROM transactions WHERE accountId = :accountId")
    fun getTransactionsByAccountId(accountId: Int): List<Transaction>

    @Query("SELECT * FROM transactions WHERE id = :transactionId")
    fun getTransactionById(transactionId: Int): Transaction

    @Insert
    fun insertTransaction(vararg transaction: Transaction)


}