package com.example.umte_project.presentation.add_edit_expense

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel
import kotlin.math.truncate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExpenseScreen(viewModel: AddEditExpenseViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = state.amount.toString(),
            onValueChange = {
                try {
                    viewModel.onAmountChange(it.toDouble())
                } catch (e: Exception) {
                    //TODO handle error
                }
            }, label = { Text("Amount") }, modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = expanded, onExpandedChange = { expanded = it }) {
            OutlinedTextField(
                value = if (state.categories.isNotEmpty()) state.categories[state.categoryId.toInt()].name else "",
                onValueChange = { selectedCategory = it },
                modifier = Modifier.menuAnchor(),
                label = { Text("Category") },
                readOnly = true,
            )
        }

        Text(state.loadingError ?: "no error")

    }
}