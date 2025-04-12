package com.example.umte_project.domain.usecase.expense

import com.example.umte_project.domain.models.Expense
import com.example.umte_project.domain.models.ExpenseWithCategory
import com.example.umte_project.domain.repository.ExpenseRepository
import com.example.umte_project.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetExpenseWithCategoryUseCase(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(id: Long): Flow<Resource<ExpenseWithCategory>> = flow {
        emit(Resource.Loading)
        try {
            val expense = expenseRepository.getExpenseWithCategory(id)
            if (expense != null) {
                emit(Resource.Success(expense))
            } else {
                emit(Resource.Error("Expense not found"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to load expense: ${e.message}"))
        }
    }
}