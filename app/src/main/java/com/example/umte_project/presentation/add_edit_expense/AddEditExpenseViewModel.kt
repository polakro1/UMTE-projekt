package com.example.umte_project.presentation.add_edit_expense

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umte_project.domain.models.Category
import com.example.umte_project.domain.models.Expense
import com.example.umte_project.domain.models.ExpenseWithCategory
import com.example.umte_project.domain.usecase.category.CategoryUseCases
import com.example.umte_project.domain.usecase.expense.ExpenseUseCases
import com.example.umte_project.domain.utils.Resource
import com.example.umte_project.presentation.add_edit_category.AddEditCategoryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

sealed interface AddEditExpenseState {
    data object Loading : AddEditExpenseState
    data class Success(
        val isEditMode: Boolean,
        val originalExpense: ExpenseWithCategory? = null,
        val amount: String = "0",
        val createdAt: ZonedDateTime = ZonedDateTime.now(),
        val categoryId: Long = 0,
        val selectedCategory: Category? = null,
        val note: String = "",
        val latitude: String = "",
        val longitude: String = "",
        val isSaving: Boolean = false,
        val savingError: String? = null,
    ) : AddEditExpenseState

    data object Saved : AddEditExpenseState
    data class Error(val message: String) : AddEditExpenseState
}

class AddEditExpenseViewModel(
    private val expenseUseCases: ExpenseUseCases, private val categoryUseCases: CategoryUseCases
) : ViewModel() {
    private val _state = MutableStateFlow<AddEditExpenseState>(AddEditExpenseState.Loading)
    val state: StateFlow<AddEditExpenseState> = _state.asStateFlow()

    fun initNewExpense() {
        _state.update { AddEditExpenseState.Success(isEditMode = false) }

    }

    fun getExpense(id: Long) {
        expenseUseCases.getExpenseWithCategory(id).onEach { result ->

            when (result) {
                is Resource.Loading -> {
                    _state.update { AddEditExpenseState.Loading }
                }

                is Resource.Success -> {
                    val expenseWithCategory = result.data
                    _state.update {
                        AddEditExpenseState.Success(
                            amount = expenseWithCategory.expense.amount.toString(),
                            createdAt = expenseWithCategory.expense.createdAt,
                            categoryId = expenseWithCategory.expense.categoryId,
                            note = expenseWithCategory.expense.note ?: "",
                            latitude = expenseWithCategory.expense.latitude?.toString() ?: "",
                            longitude = expenseWithCategory.expense.longitude?.toString() ?: "",
                            isEditMode = true,
                            originalExpense = expenseWithCategory,
                            selectedCategory = expenseWithCategory.category
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        AddEditExpenseState.Error(result.message)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadCategoryById(id: Long) {
        categoryUseCases.getCategory(id).onEach { result ->

            when (result) {
                is Resource.Loading -> {
                    _state.update { AddEditExpenseState.Loading }
                }

                is Resource.Error -> {
                    _state.update {
                        AddEditExpenseState.Error(result.message)
                    }
                }

                is Resource.Success -> {
                    _state.update {
                        AddEditExpenseState.Success(
                            selectedCategory = result.data, isEditMode = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onAmountChange(newAmount: String) {
        val currentState = _state.value
        if (currentState is AddEditExpenseState.Success) {
            _state.update { currentState.copy(amount = newAmount) }
        }
    }

    fun onCreatedAtChange(newCreatedAt: ZonedDateTime) {
        val currentState = _state.value
        if (currentState is AddEditExpenseState.Success) {
            _state.update { currentState.copy(createdAt = newCreatedAt) }
        }
    }

    fun onCategoryIdChange(newCategoryId: Long) {
        val currentState = _state.value
        if (currentState is AddEditExpenseState.Success) {
            _state.update { currentState.copy(categoryId = newCategoryId) }
        }
    }

    fun onNoteChanged(newNote: String) {
        val currentState = _state.value
        if (currentState is AddEditExpenseState.Success) {
            _state.update { currentState.copy(note = newNote) }
        }
    }

    fun onLatitudeChange(newLatitude: String) {
        val currentState = _state.value
        if (currentState is AddEditExpenseState.Success) {
            _state.update { currentState.copy(latitude = newLatitude) }
        }
    }

    fun onLongitudeChange(newLongitude: String) {
        val currentState = _state.value
        if (currentState is AddEditExpenseState.Success) {
            _state.update { currentState.copy(longitude = newLongitude) }
        }
    }

    fun saveExpense(onSave: () -> Unit) {
        val currentState = _state.value as? AddEditExpenseState.Success ?: return
        viewModelScope.launch {
            _state.update { currentState.copy(isSaving = true, savingError = null) }
            try {
                val amountError = validateAmount(currentState.amount)
                if (amountError != null) {
                    _state.update {
                        currentState.copy(
                            isSaving = false,
                            savingError = amountError
                        )
                    }
                    Log.d("TEST", amountError)
                    return@launch
                }

                val amount = currentState.amount.toDouble()

                val expense = Expense(
                    id = currentState.originalExpense?.expense?.id ?: 0,
                    amount = amount,
                    createdAt = currentState.createdAt,
                    categoryId = currentState.selectedCategory?.id ?: return@launch,
                    note = if (currentState.note.isBlank()) null else currentState.note,
                    longitude = if (currentState.longitude.isBlank()) null else currentState.longitude.toDoubleOrNull(),
                    latitude = if (currentState.latitude.isBlank()) null else currentState.latitude.toDoubleOrNull()
                )

                expenseUseCases.addExpense(expense)

                onSave()

            } catch (e: Exception) {
                _state.update {
                    currentState.copy(
                        isSaving = false, savingError = "Saving failed: ${e.message}"
                    )
                }
            }
        }

    }

    fun validateAmount(input: String): String? {
        // Normalizuj čárku na tečku
        val normalized = input.replace(',', '.')

        // Ověř, zda je číslo validní
        val parsed = normalized.toDoubleOrNull()
            ?: return "Amount must be a valid number"

        if (parsed >= 0.0) {
            return "Amount must be lower than 0"
        }

        // Nepovoluj čísla jako 01, 004, 00.5 apod.
        val withoutLeadingZeros = normalized.trimStart('0')

        // Povolit jen "0" nebo čísla bez vedoucích nul
        if (normalized.startsWith("0") &&
            !normalized.startsWith("0.") &&
            normalized != "0"
        ) {
            return "Invalid number format"
        }

        return null
    }
}