package com.example.umte_project.presentation.add_edit_expense

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun AddEditExpenseScreen(viewModel: AddEditExpenseViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    Column {
        OutlinedTextField(
            value = state.amount.toString(), onValueChange = {
                try {
                    viewModel.onAmountChange(it.toDouble())
                } catch (e: Exception) {
                    //TODO handle error
                }
            },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )

    }
}