package com.example.umte_project.presentation.dashboard

import android.util.Log
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
import java.time.LocalDate
import java.time.YearMonth

sealed interface DashboardState {
    data object Loading : DashboardState

    data class Success(
        val monthlyExpenses: Double?,
        val todaysExpenses: Double?,
        val recentExpenses: List<ExpenseWithCategory>
    ) : DashboardState

    data object Saved : DashboardState
    data class Error(val message: String) : DashboardState
}

class DashboardViewModel(private val expenseUseCases: ExpenseUseCases) : ViewModel() {
    private val _state = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        expenseUseCases.getExpensesWithCategory().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.update { DashboardState.Loading }
                }

                is Resource.Success -> {
                    val allExpenses = result.data

                    val today = LocalDate.now()
                    val currentMonth = YearMonth.now()

                    val monthlyExpenses = allExpenses
                        .filter { YearMonth.from(it.expense.createdAt.toLocalDate()) == currentMonth }
                        .sumOf { it.expense.amount }

                    val todaysExpenses = allExpenses
                        .filter { it.expense.createdAt.toLocalDate() == today }
                        .sumOf { it.expense.amount }

                    val recentExpenes = allExpenses
                        .sortedByDescending { it.expense.createdAt }
                        .take(5)

                    _state.update {
                        DashboardState.Success(
                            monthlyExpenses = monthlyExpenses,
                            todaysExpenses = todaysExpenses,
                            recentExpenses = recentExpenes
                        )
                    }
                }

                is Resource.Error -> {
                    Log.d("DB", result.message)
                    _state.update { DashboardState.Error(result.message) }
                }
            }
        }.launchIn(viewModelScope)
    }
}