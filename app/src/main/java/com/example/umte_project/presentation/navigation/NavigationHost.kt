package com.example.umte_project.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.umte_project.presentation.add_edit_expense.AddEditExpenseScreen
import com.example.umte_project.presentation.expense_detail.ExpenseDetailScreen
import com.example.umte_project.presentation.expense_list.ExpenseListScreen

@Composable
fun NavigationHost(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Routes.EXPENSE_LIST,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Routes.EXPENSE_LIST) {
            ExpenseListScreen(navController = navController)
        }
        composable(Routes.ADD_EDIT_EXPENSE) {
            AddEditExpenseScreen()
        }
        composable(
            route = Routes.EXPENSE_DETAIL,
            arguments = listOf(navArgument("expenseId") { type = NavType.LongType })
        ) { navBackStackEntry ->
            val expenseId = navBackStackEntry.arguments?.getLong("expenseId")
            if (expenseId != null) {
                ExpenseDetailScreen(expenseId = expenseId)
            }
        }
    }
}