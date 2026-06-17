package com.example.blushbuddy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.blushbuddy.data.repository.BlushBuddyRepository
import com.example.blushbuddy.ui.screen.home.HomeScreen
import com.example.blushbuddy.ui.screen.login.LoginScreen
import com.example.blushbuddy.ui.screen.redeem.RedeemScreen
import com.example.blushbuddy.ui.screen.register.RegisterScreen
import com.example.blushbuddy.ui.screen.transaction.AddTransactionScreen
import com.example.blushbuddy.ui.screen.transaction.TransactionScreen
import com.example.blushbuddy.ui.screen.welcome.WelcomeScreen

object Routes {
    const val WELCOME = "welcome"
    const val REGISTER = "register"
    const val LOGIN = "login"
    const val HOME = "home/{memberId}"
    const val TRANSACTIONS = "transactions/{memberId}"
    const val ADD_TRANSACTION = "add_transaction/{memberId}"
    const val REDEEM = "redeem/{memberId}"

    fun home(memberId: Int) = "home/$memberId"
    fun transactions(memberId: Int) = "transactions/$memberId"
    fun addTransaction(memberId: Int) = "add_transaction/$memberId"
    fun redeem(memberId: Int) = "redeem/$memberId"
}

@Composable
fun BlushBuddyNavGraph(
    navController: NavHostController,
    repository: BlushBuddyRepository
) {
    NavHost(navController = navController, startDestination = Routes.WELCOME) {

        composable(Routes.WELCOME) {
            WelcomeScreen(
                onRegisterClick = { navController.navigate(Routes.REGISTER) },
                onLoginClick = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                repository = repository,
                onSuccess = { memberId ->
                    navController.navigate(Routes.home(memberId)) {
                        popUpTo(Routes.WELCOME) { inclusive = false }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                repository = repository,
                onSuccess = { memberId ->
                    navController.navigate(Routes.home(memberId)) {
                        popUpTo(Routes.WELCOME) { inclusive = false }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.HOME,
            arguments = listOf(navArgument("memberId") { type = NavType.IntType })
        ) { backStackEntry ->
            val memberId = backStackEntry.arguments?.getInt("memberId") ?: return@composable
            HomeScreen(
                memberId = memberId,
                repository = repository,
                onTransactionsClick = { navController.navigate(Routes.transactions(memberId)) },
                onRedeemClick = { navController.navigate(Routes.redeem(memberId)) },
                onLogout = {
                    navController.navigate(Routes.WELCOME) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Routes.TRANSACTIONS,
            arguments = listOf(navArgument("memberId") { type = NavType.IntType })
        ) { backStackEntry ->
            val memberId = backStackEntry.arguments?.getInt("memberId") ?: return@composable
            TransactionScreen(
                memberId = memberId,
                repository = repository,
                onAddClick = { navController.navigate(Routes.addTransaction(memberId)) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.ADD_TRANSACTION,
            arguments = listOf(navArgument("memberId") { type = NavType.IntType })
        ) { backStackEntry ->
            val memberId = backStackEntry.arguments?.getInt("memberId") ?: return@composable
            AddTransactionScreen(
                memberId = memberId,
                repository = repository,
                onSuccess = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.REDEEM,
            arguments = listOf(navArgument("memberId") { type = NavType.IntType })
        ) { backStackEntry ->
            val memberId = backStackEntry.arguments?.getInt("memberId") ?: return@composable
            RedeemScreen(
                memberId = memberId,
                repository = repository,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
