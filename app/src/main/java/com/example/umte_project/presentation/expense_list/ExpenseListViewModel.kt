package com.example.umte_project.presentation.expense_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.umte_project.domain.models.Expense
import com.example.umte_project.domain.usecase.expense.ExpenseUseCases
import com.example.umte_project.domain.utils.Resource
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

data class ExpenseListState(
    val expenses: List<Expense> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ExpenseListViewModel(private val expenseUseCases: ExpenseUseCases) : ViewModel() {
    private val _state = MutableStateFlow(ExpenseListState())
    val state: StateFlow<ExpenseListState> = _state.asStateFlow()

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        expenseUseCases.getExpenses().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            expenses = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }
}