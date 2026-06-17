package com.example.blushbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "members")
data class MemberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String,
    val phone: String,
    val memberNumber: String,
    val totalPoints: Int = 0,
    val registrationDate: Long = System.currentTimeMillis()
)
