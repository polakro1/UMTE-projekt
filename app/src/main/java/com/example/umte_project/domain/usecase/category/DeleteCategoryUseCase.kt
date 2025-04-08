package com.example.umte_project.domain.usecase.category

import com.example.umte_project.domain.models.Category
import com.example.umte_project.domain.repository.CategoryRepository

class DeleteCategoryUseCase(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(category: Category) {
        categoryRepository.deleteCategory(category)
    }
}