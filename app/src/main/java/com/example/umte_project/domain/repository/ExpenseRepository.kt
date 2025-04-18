package com.example.umte_project.domain.repository

import com.example.umte_project.domain.models.Expense
import com.example.umte_project.domain.models.ExpenseWithCategory
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAllExpenses(): Flow<List<Expense>>
    suspend fun getExpenseById(id: Long): Expense?
    suspend fun addExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
    fun getExpensesWithCategory(): Flow<List<ExpenseWithCategory>>
    suspend fun getExpenseWithCategory(id: Long): ExpenseWithCategory?
}