package com.example.umte_project.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.umte_project.data.local.entities.ExpenseEntity
import com.example.umte_project.data.local.entities.ExpenseWithCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses order by createdAtMillis DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE id = :id")
    suspend fun getExpenseById(id: Long): ExpenseEntity?

    @Transaction
    @Query("SELECT * FROM expenses ORDER BY createdAtMillis DESC")
    fun getExpensesWithCategory(): Flow<List<ExpenseWithCategoryEntity>>
}