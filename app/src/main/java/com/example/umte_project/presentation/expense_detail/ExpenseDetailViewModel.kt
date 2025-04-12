package com.example.umte_project.presentation.expense_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umte_project.domain.models.ExpenseWithCategory
import com.example.umte_project.domain.usecase.expense.ExpenseUseCases
import com.example.umte_project.domain.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update


sealed interface ExpenseDetailState {
    data object Loading : ExpenseDetailState
    data class Error(val message: String) : ExpenseDetailState
    data class Success(val expense: ExpenseWithCategory) : ExpenseDetailState
}


class ExpenseDetailViewModel(private val expenseUseCases: ExpenseUseCases) : ViewModel() {
    private val _state = MutableStateFlow<ExpenseDetailState>(ExpenseDetailState.Loading)
    val state: StateFlow<ExpenseDetailState> = _state.asStateFlow()

    fun getExpense(id: Long) {
        expenseUseCases.getExpenseWithCategory(id).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.update { ExpenseDetailState.Loading }
                }

                is Resource.Success -> {
                    _state.update { ExpenseDetailState.Success(result.data) }
                }

                is Resource.Error -> {
                    _state.update {
                        ExpenseDetailState.Error(result.message ?: "Unknown error")
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}