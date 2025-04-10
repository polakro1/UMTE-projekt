package com.example.umte_project.domain.usecase.expense

import com.example.umte_project.domain.models.Expense
import com.example.umte_project.domain.repository.ExpenseRepository
import com.example.umte_project.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetExpenseUseCase(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(id: Long): Flow<Resource<Expense>> = flow {
        emit(Resource.Loading)
        try {
            val expense = expenseRepository.getExpenseById(id)
            if (expense != null) {
                emit(Resource.Succes(expense))
            } else {
                emit(Resource.Error("Expense not found"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to load expense: ${e.message}"))
        }
    }
}