package com.example.umte_project.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.umte_project.data.local.dao.CategoryDao
import com.example.umte_project.data.local.dao.ExpenseDao
import com.example.umte_project.data.local.entities.CategoryEntity
import com.example.umte_project.data.local.entities.ExpenseEntity

@Database(
    entities = [ExpenseEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao
}