package com.example.umte_project.domain.usecase.expense

import com.example.umte_project.domain.models.Expense
import com.example.umte_project.domain.repository.ExpenseRepository

class GetExpenseUseCase(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(id: Long): Expense? {
        return expenseRepository.getExpenseById(id)
    }
}