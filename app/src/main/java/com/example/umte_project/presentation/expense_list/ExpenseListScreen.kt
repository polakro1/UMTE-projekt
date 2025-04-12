package com.example.umte_project.presentation.expense_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.umte_project.domain.models.Expense
import com.example.umte_project.domain.models.ExpenseWithCategory
import com.example.umte_project.presentation.navigation.Routes
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ExpenseListScreen(
    viewModel: ExpenseListViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    Column {
        LazyColumn {
            items(state.expenses) { expense ->
                ExpenseItem(
                    expense = expense,
                    onClick = { navController.navigate(Routes.expenseDetail(expense.expense.id)) })
            }
        }
    }

}

@Composable
fun ExpenseItem(expense: ExpenseWithCategory, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Column {
                Text(text = expense.category.name)
                Text(text = expense.expense.note ?: "")
            }
            Column {
                Text(text = expense.expense.amount.toString())
                Text(text = expense.expense.createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
            }
        }
    }

}