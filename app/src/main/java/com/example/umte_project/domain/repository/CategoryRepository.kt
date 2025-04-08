package com.example.umte_project.domain.repository

import com.example.umte_project.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<Category>>
    suspend fun getCategoryById(id: Long): Category?
    suspend fun addCategory(category: Category)
    suspend fun deleteCategory(category: Category)
}