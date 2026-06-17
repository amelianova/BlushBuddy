package com.example.blushbuddy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.blushbuddy.data.local.entity.MemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Insert
    suspend fun insert(member: MemberEntity): Long

    @Update
    suspend fun update(member: MemberEntity)

    @Query("SELECT * FROM members WHERE id = :id")
    fun getMemberById(id: Int): Flow<MemberEntity?>

    @Query("SELECT * FROM members WHERE email = :email LIMIT 1")
    suspend fun getMemberByEmail(email: String): MemberEntity?

    @Query("SELECT * FROM members ORDER BY registrationDate DESC")
    fun getAllMembers(): Flow<List<MemberEntity>>

    @Query("UPDATE members SET totalPoints = totalPoints + :points WHERE id = :memberId")
    suspend fun addPoints(memberId: Int, points: Int)

    @Query("UPDATE members SET totalPoints = totalPoints - :points WHERE id = :memberId")
    suspend fun deductPoints(memberId: Int, points: Int)
}
