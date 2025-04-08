package com.example.umte_project.domain.usecase.category

class CategoryUseCases(
    val getCategories: GetCategoriesUseCase,
    val getCategory: GetCategoryUseCase,
    val addCategory: AddCategoryUseCase,
    val deleteCategory: DeleteCategoryUseCase
) {
}