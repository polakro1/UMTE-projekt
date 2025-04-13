package com.example.umte_project.presentation.category_select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umte_project.domain.models.Category
import com.example.umte_project.domain.usecase.category.CategoryUseCases
import com.example.umte_project.domain.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

data class SelectCategoryState(
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val isLoading: Boolean = false,
    val loadingError: String? = null
)

class SelectCategoryViewModel(private val categoryUseCases: CategoryUseCases) : ViewModel() {
    private val _state = MutableStateFlow(SelectCategoryState())
    val state = _state.asStateFlow()

    fun loadCategories() {
        categoryUseCases.getCategories().onEach { result ->

            when (result) {
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            loadingError = result.message
                        )
                    }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            categories = result.data,
                            isLoading = false,
                            loadingError = null
                        )
                    }
                    selectInitialCategory()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun selectInitialCategory() {
        if (_state.value.selectedCategory != null) {
            val id = _state.value.selectedCategory!!.id
            val category = _state.value.categories.find { it.id == id }
            if (category != null) {
                _state.update { it.copy(selectedCategory = category) }
            }
        }

    }

    fun onCategorySelected(category: Category, onSave: () -> Unit) {
        _state.update {
            it.copy(
                selectedCategory = category,
            )
        }

        onSave()
    }
}