package com.example.umte_project.data.repository

import com.example.umte_project.data.local.dao.CategoryDao
import com.example.umte_project.data.local.mappers.toDomain
import com.example.umte_project.data.local.mappers.toEntity
import com.example.umte_project.domain.models.Category
import com.example.umte_project.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(private val dao: CategoryDao) : CategoryRepository {
    override fun getAllCategories(): Flow<List<Category>> {
        return dao.getAllCategories().map { categoryEntities ->
            categoryEntities.map { it.toDomain() }
        }
    }

    override suspend fun getCategoryById(id: Long): Category? {
        return dao.getCategoryById(id)?.toDomain()
    }

    override suspend fun addCategory(category: Category) {
        dao.insertCategory(category.toEntity())
    }

    override suspend fun deleteCategory(category: Category) {
        dao.deleteCategory(category.toEntity())
    }
}