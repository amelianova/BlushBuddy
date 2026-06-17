package com.example.blushbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.blushbuddy.data.local.BlushBuddyDatabase
import com.example.blushbuddy.data.repository.BlushBuddyRepository
import com.example.blushbuddy.ui.navigation.BlushBuddyNavGraph
import com.example.blushbuddy.ui.theme.BlushBuddyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = BlushBuddyDatabase.getDatabase(this)
        val repository = BlushBuddyRepository(
            memberDao = database.memberDao(),
            transactionDao = database.transactionDao()
        )

        setContent {
            BlushBuddyTheme {
                val navController = rememberNavController()
                BlushBuddyNavGraph(
                    navController = navController,
                    repository = repository
                )
            }
        }
    }
}
