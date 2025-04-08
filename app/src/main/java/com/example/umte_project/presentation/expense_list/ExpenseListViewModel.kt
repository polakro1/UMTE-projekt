package com.example.umte_project.presentation.expense_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.umte_project.domain.models.Expense
import com.example.umte_project.domain.usecase.expense.ExpenseUseCases
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExpenseListViewModel(private val expenseUseCases: ExpenseUseCases) : ViewModel() {
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            expenseUseCases.getExpenses().collectLatest { list ->
                _expenses.value = list
            }
        }
    }
}