package com.example.umte_project.presentation.ui_components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.umte_project.domain.models.ExpenseWithCategory
import com.example.umte_project.presentation.navigation.Routes

@Composable
fun ExpenseList(expenses: List<ExpenseWithCategory>) {
    val navController: NavController = rememberNavController()

    LazyColumn(userScrollEnabled = true) {
        items(expenses) { expense ->
            ExpenseListItem(
                expense = expense,
                onClick = { navController.navigate(Routes.expenseDetail(expense.expense.id)) })

            if (expense != expenses.last()) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 0.dp, vertical = 8.dp),
                    color = MaterialTheme.colorScheme.surfaceDim
                )
            }
        }
    }
}