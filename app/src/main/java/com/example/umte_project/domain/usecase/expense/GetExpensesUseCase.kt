package com.example.umte_project.domain.usecase.expense

import com.example.umte_project.domain.models.Expense
import com.example.umte_project.domain.repository.ExpenseRepository
import com.example.umte_project.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetExpensesUseCase(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(): Flow<Resource<List<Expense>>> = flow {
        emit(Resource.Loading)
        try {
            expenseRepository.getAllExpenses().collect { expenses
                ->
                emit(Resource.Succes(expenses))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to load expenses: ${e.message}"))
        }
    }
}