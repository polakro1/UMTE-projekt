package com.example.umte_project.domain.usecase.expense

import com.example.umte_project.domain.models.ExpenseWithCategory
import com.example.umte_project.domain.repository.ExpenseRepository
import com.example.umte_project.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetExpensesWithCategoryUseCase(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(): Flow<Resource<List<ExpenseWithCategory>>> = flow {
        emit(Resource.Loading)
        try {
            expenseRepository.getExpensesWithCategory().collect { expenses ->
                emit(Resource.Success(expenses))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to load expenses: ${e.message}"))
        }
    }
}