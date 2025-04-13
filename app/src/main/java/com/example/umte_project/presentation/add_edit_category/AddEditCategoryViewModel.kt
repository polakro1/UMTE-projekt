package com.example.umte_project.presentation.add_edit_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umte_project.domain.models.Category
import com.example.umte_project.domain.usecase.category.CategoryUseCases
import com.example.umte_project.domain.utils.Resource
import com.example.umte_project.presentation.ui_components.CategoryIcons
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AddEditCategoryState {
    data object Loading : AddEditCategoryState

    data class Success(
        val isEditMode: Boolean,
        val originalCategory: Category? = null,
        val name: String = "",
        val selectedColor: String = "4CAF50",
        val selectedIcon: String = CategoryIcons.FOOD.iconName,
        val isSaving: Boolean = false,
        val savingError: String? = null
    ) : AddEditCategoryState

    data object Saved : AddEditCategoryState
    data class Error(val message: String) : AddEditCategoryState
}


class AddEditCategoryViewModel(private val categoryUseCases: CategoryUseCases) : ViewModel() {
    private val _state = MutableStateFlow<AddEditCategoryState>(AddEditCategoryState.Loading)
    val state = _state.asStateFlow()

    fun initNewCategory() {
        _state.update {
            AddEditCategoryState.Success(
                isEditMode = false,
            )
        }
    }

    fun getCategory(id: Long) {
        categoryUseCases.getCategory(id).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.update { AddEditCategoryState.Loading }
                }

                is Resource.Success -> {
                    val category = result.data
                    _state.update {
                        AddEditCategoryState.Success(
                            name = category.name,
                            selectedIcon = category.iconRes,
                            selectedColor = category.colorHex,
                            isEditMode = true
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        AddEditCategoryState.Error(result.message)
                    }
                }
            }
        }
    }

    fun onNameChange(newName: String) {
        val currentState = _state.value
        if (currentState is AddEditCategoryState.Success) {
            _state.update { currentState.copy(name = newName) }
        }
    }


    fun onColorChange(newColor: String) {
        val currentState = _state.value
        if (currentState is AddEditCategoryState.Success) {
            _state.update { currentState.copy(selectedColor = newColor) }
        }
    }

    fun onIconChange(newIcon: String) {
        val currentState = _state.value
        if (currentState is AddEditCategoryState.Success) {
            _state.update { currentState.copy(selectedIcon = newIcon) }
        }
    }


    fun saveCategory(onSave: () -> Unit) {
        val currentState = _state.value as? AddEditCategoryState.Success ?: return

        viewModelScope.launch {
            _state.update { currentState.copy(isSaving = true, savingError = null) }
            try {
                val category = Category(
                    id = currentState.originalCategory?.id ?: 0,
                    name = currentState.name,
                    iconRes = currentState.selectedIcon,
                    colorHex = currentState.selectedColor
                )
                categoryUseCases.addCategory(category)
                onSave()
            } catch (e: Exception) {
                _state.update {
                    currentState.copy(
                        isSaving = false, savingError = "Saving failed: ${e.message}"
                    )
                }
            } finally {
                _state.update { currentState.copy(isSaving = false) }
            }
        }
    }
}
