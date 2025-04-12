package com.example.umte_project.presentation.expense_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.umte_project.domain.models.ExpenseWithCategory
import com.example.umte_project.presentation.ui_components.CategoryIcon
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExpenseDetailScreen(
    viewModel: ExpenseDetailViewModel = koinViewModel(),
    expenseId: Long
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getExpense(expenseId)
    }

    when (val currentState = state) {
        is ExpenseDetailState.Loading -> CircularProgressIndicator()
        is ExpenseDetailState.Error -> Text(currentState.message)
        is ExpenseDetailState.Success -> ExpenseDetailContent(expense = currentState.expense)
    }

}

@Composable
fun ExpenseDetailContent(expense: ExpenseWithCategory) {
    Column {
        Text(text = expense.expense.amount.toString())
        CategoryIcon(expense.category.iconRes)
    }
}