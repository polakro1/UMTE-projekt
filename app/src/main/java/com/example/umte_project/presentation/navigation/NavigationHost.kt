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
import com.example.umte_project.presentation.add_edit_category.AddEditCategoryScreen
import com.example.umte_project.presentation.add_edit_expense.AddEditExpenseScreen
import com.example.umte_project.presentation.category_select.SelectCategoryScreen
import com.example.umte_project.presentation.dashboard.DashboardScreen
import com.example.umte_project.presentation.expense_detail.ExpenseDetailScreen
import com.example.umte_project.presentation.expense_list.ExpenseListScreen
import com.example.umte_project.presentation.select_location.SelectLocationScreen
import com.example.umte_project.presentation.settings.SettingsScreen

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
        composable(Routes.ADD_EXPENSE) {
            AddEditExpenseScreen(navController = navController)
        }

        composable(route = Routes.SELECT_CATEGORY) {
            SelectCategoryScreen(navController = navController)
        }

        composable(route = Routes.ADD_CATEGORY) {
            AddEditCategoryScreen(navController)
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

        composable(route = Routes.SELECT_LOCATION) {
            SelectLocationScreen(navController = navController)
        }

        composable(route = Routes.SETTINGS) {
            SettingsScreen()
        }

        composable(route = Routes.DASHBOARD) {
            DashboardScreen()
        }

    }
}