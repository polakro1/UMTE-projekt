package com.example.umte_project.domain.usecase.expense

import com.example.umte_project.domain.models.Expense
import com.example.umte_project.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetExpensesUseCase(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<Expense>> {
        return expenseRepository.getAllExpenses()
    }
}