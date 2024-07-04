package com.example.banking

class AccountRepository(private val accountDao: AccountDao) {

    fun insertAccounts(vararg accounts: Account) {
        accountDao.insertAccounts(*accounts)
    }
    fun getAllAccounts(): List<Account> {
        return accountDao.getAllAccounts()
    }

    fun getAccountIdByName(name: String): Int {
        return accountDao.getAccountIdByName(name)
    }

    fun insertTransaction(transaction: Transaction) {
        accountDao.insertTransaction(transaction)
    }

    fun getTransactionsByAccountId(accountId: Int): List<Transaction> {
        return accountDao.getTransactionsByAccountId(accountId)
    }
}