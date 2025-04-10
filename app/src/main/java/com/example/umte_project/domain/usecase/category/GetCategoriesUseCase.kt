package com.example.umte_project.domain.usecase.category

import com.example.umte_project.domain.models.Category
import kotlinx.coroutines.flow.Flow
import com.example.umte_project.domain.repository.CategoryRepository
import com.example.umte_project.domain.utils.Resource
import kotlinx.coroutines.flow.flow

class GetCategoriesUseCase(private val categoryRepository: CategoryRepository) {
    operator fun invoke(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading)
        try {
            categoryRepository.getAllCategories().collect { categories ->
                emit(Resource.Success(categories))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to load categories: ${e.message}"))
        }
    }
}