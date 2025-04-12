package com.example.umte_project.presentation.expense_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umte_project.data.local.entities.ExpenseWithCategoryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.umte_project.domain.models.Expense
import com.example.umte_project.domain.models.ExpenseWithCategory
import com.example.umte_project.domain.usecase.expense.ExpenseUseCases
import com.example.umte_project.domain.utils.Resource
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

data class ExpenseListState(
    val expenses: List<ExpenseWithCategory> = emptyList(),
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
        Log.d("DB", "Loading expenses")
        expenseUseCases.getExpensesWithCategory().onEach { result ->
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
                    Log.d("DB", result.message)
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }.launchIn(viewModelScope)
    }
}