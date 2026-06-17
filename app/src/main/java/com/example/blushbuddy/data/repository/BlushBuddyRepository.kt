package com.example.blushbuddy.data.repository

import com.example.blushbuddy.data.local.dao.MemberDao
import com.example.blushbuddy.data.local.dao.TransactionDao
import com.example.blushbuddy.data.local.entity.MemberEntity
import com.example.blushbuddy.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BlushBuddyRepository(
    private val memberDao: MemberDao,
    private val transactionDao: TransactionDao
) {
    suspend fun registerMember(name: String, email: String, phone: String): Long {
        val dateStr = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val memberNumber = "BB-$dateStr-${(1000..9999).random()}"
        val member = MemberEntity(
            name = name,
            email = email,
            phone = phone,
            memberNumber = memberNumber
        )
        return memberDao.insert(member)
    }

    suspend fun loginMember(email: String): MemberEntity? =
        memberDao.getMemberByEmail(email)

    fun getMemberById(id: Int): Flow<MemberEntity?> =
        memberDao.getMemberById(id)

    fun getTransactions(memberId: Int): Flow<List<TransactionEntity>> =
        transactionDao.getTransactionsByMember(memberId)

    suspend fun addTransaction(memberId: Int, amount: Long, description: String) {
        val points = (amount / 10000).toInt()
        transactionDao.insert(
            TransactionEntity(
                memberId = memberId,
                amount = amount,
                pointsEarned = points,
                description = description
            )
        )
        memberDao.addPoints(memberId, points)
    }

    suspend fun redeemReward(memberId: Int, pointsCost: Int): Boolean {
        val member = memberDao.getMemberById(memberId)
        return true.also {
            memberDao.deductPoints(memberId, pointsCost)
        }
    }

    suspend fun getMemberOnce(memberId: Int): MemberEntity? {
        var result: MemberEntity? = null
        getMemberById(memberId).collect { result = it }
        return result
    }
}
