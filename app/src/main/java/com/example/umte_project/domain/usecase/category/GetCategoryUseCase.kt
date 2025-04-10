package com.example.umte_project.domain.usecase.category

import com.example.umte_project.domain.models.Category
import com.example.umte_project.domain.repository.CategoryRepository
import com.example.umte_project.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetCategoryUseCase(private val categoryRepository: CategoryRepository) {
    operator fun invoke(id: Long): Flow<Resource<Category>> = flow {
        emit(Resource.Loading)
        try {
            val category = categoryRepository.getCategoryById(id)
            if (category != null) {
                emit(Resource.Success(category))
            } else {
                emit(Resource.Error("Category not found"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to load category: ${e.message}"))
        }
    }
}