package com.example.umte_project.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.umte_project.presentation.ui_components.ExpenseListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (val state = state) {
        is DashboardState.Loading -> CircularProgressIndicator()
        is DashboardState.Error -> Text(state.message)
        is DashboardState.Saved -> {}
        is DashboardState.Success -> {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Přehled za ${
                        java.time.YearMonth.now().month.name.lowercase()
                            .replaceFirstChar { it.uppercase() }
                    }",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Celkové výdaje: ${state.monthlyExpenses?.toInt()} Kč",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Dnešní výdaje: ${state.todaysExpenses?.toInt()} Kč",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Poslední výdaje:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.recentExpenses) { expense ->
                        ExpenseListItem(expense = expense, onClick = {})
                    }
                }
            }
        }
    }

}