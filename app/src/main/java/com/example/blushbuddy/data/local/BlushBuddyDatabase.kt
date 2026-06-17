package com.example.blushbuddy.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.blushbuddy.data.local.dao.MemberDao
import com.example.blushbuddy.data.local.dao.TransactionDao
import com.example.blushbuddy.data.local.entity.MemberEntity
import com.example.blushbuddy.data.local.entity.TransactionEntity

@Database(
    entities = [MemberEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BlushBuddyDatabase : RoomDatabase() {
    abstract fun memberDao(): MemberDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: BlushBuddyDatabase? = null

        fun getDatabase(context: Context): BlushBuddyDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    BlushBuddyDatabase::class.java,
                    "blushbuddy_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
