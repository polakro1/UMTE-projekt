package com.example.umte_project.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.umte_project.presentation.add_edit_expense.AddEditExpenseScreen
import com.example.umte_project.presentation.expense_list.ExpenseListScreen

@Composable
fun NavigationHost(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Routes.EXPENSE_LIST,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Routes.EXPENSE_LIST) {
            ExpenseListScreen()
        }
        composable(Routes.ADD_EDIT_EXPENSE) {
            AddEditExpenseScreen()
        }
    }
}