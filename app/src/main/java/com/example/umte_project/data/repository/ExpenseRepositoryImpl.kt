package com.example.umte_project.data.repository

import com.example.umte_project.data.local.dao.ExpenseDao
import com.example.umte_project.data.mappers.toDomain
import com.example.umte_project.data.mappers.toEntity
import com.example.umte_project.domain.models.Expense
import com.example.umte_project.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepositoryImpl(private val dao: ExpenseDao) : ExpenseRepository {
    override fun getAllExpenses(): Flow<List<Expense>> {
        return dao.getAllExpenses().map { expenseEntities ->
            expenseEntities.map { it.toDomain() }
        }
    }

    override suspend fun getExpenseById(id: Long): Expense? {
        return dao.getExpenseById(id)?.toDomain()
    }

    override suspend fun addExpense(expense: Expense) {
        dao.insertExpense(expense.toEntity())
    }

    override suspend fun deleteExpense(expense: Expense) {
        dao.deleteExpense(expense.toEntity())
    }
}
