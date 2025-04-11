package com.example.umte_project.presentation.add_edit_expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umte_project.di.viewModelModule
import com.example.umte_project.domain.models.Category
import com.example.umte_project.domain.models.Expense
import com.example.umte_project.domain.usecase.category.CategoryUseCases
import com.example.umte_project.domain.usecase.expense.ExpenseUseCases
import com.example.umte_project.domain.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Error
import java.time.ZonedDateTime

data class AddEditExpenseState(
    val expense: Expense? = null,
    val amount: Double = 0.0,
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
    val categoryId: Long = 0,
    val note: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val isSaving: Boolean = false,
    val loadingError: String? = null,
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList()
)

class AddEditExpenseViewModel(
    private val expenseUseCases: ExpenseUseCases,
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(AddEditExpenseState())
    val state: StateFlow<AddEditExpenseState> = _state.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
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
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onAmountChange(newAmount: Double) {
        _state.update { it.copy(amount = newAmount) }
    }

    fun onCreatedAtChange(newCreatedAt: ZonedDateTime) {
        _state.update { it.copy(createdAt = newCreatedAt) }
    }

    fun onCategoryIdChange(newCategoryId: Long) {
        _state.update { it.copy(categoryId = newCategoryId) }
    }

    fun onNoteChanged(newNote: String) {
        _state.update { it.copy(note = newNote) }
    }

    fun onLatitudeChange(newLatitude: String) {
        _state.update { it.copy(latitude = newLatitude) }
    }

    fun onLongitudeChange(newLongitude: String) {
        _state.update { it.copy(longitude = newLongitude) }
    }

    fun saveExpense(onSave: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, loadingError = null) }
            try {
                val expense = Expense(
                    id = state.value.expense?.id ?: 0,
                    amount = state.value.amount,
                    createdAt = state.value.createdAt,
                    categoryId = state.value.categoryId,
                    note = if (state.value.note.isBlank()) null else state.value.note,
                    longitude = if (state.value.longitude.isBlank()) null else state.value.longitude.toDoubleOrNull(),
                    latitude = if (state.value.latitude.isBlank()) null else state.value.latitude.toDoubleOrNull()
                )

                expenseUseCases.addExpense(expense)

                onSave()

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isSaving = false,
                        loadingError = "Exception while adding expense: ${e.message}"
                    )
                }
            }
        }

    }

}