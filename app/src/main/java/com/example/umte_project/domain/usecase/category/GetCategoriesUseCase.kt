package com.example.umte_project.domain.usecase.category

import com.example.umte_project.domain.models.Category
import kotlinx.coroutines.flow.Flow
import com.example.umte_project.domain.repository.CategoryRepository

class GetCategoriesUseCase(private val categoryRepository: CategoryRepository) {
    operator fun invoke(): Flow<List<Category>> {
        return categoryRepository.getAllCategories()
    }
}