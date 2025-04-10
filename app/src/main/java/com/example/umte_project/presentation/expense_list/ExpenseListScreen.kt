package com.example.umte_project.presentation.expense_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.umte_project.domain.models.Expense
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExpenseListScreen(viewModel: ExpenseListViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    Text("Ahoj")
    LazyColumn {
        items(state.expenses) { expense ->
            ExpenseItem(expense = expense)
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    Card() { }
}