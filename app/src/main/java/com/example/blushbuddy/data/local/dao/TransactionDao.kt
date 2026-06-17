package com.example.blushbuddy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.blushbuddy.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE memberId = :memberId ORDER BY transactionDate DESC")
    fun getTransactionsByMember(memberId: Int): Flow<List<TransactionEntity>>
}
