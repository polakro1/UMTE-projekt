package com.example.umte_project.di

import com.example.umte_project.domain.usecase.category.AddCategoryUseCase
import com.example.umte_project.domain.usecase.category.CategoryUseCases
import com.example.umte_project.domain.usecase.category.DeleteCategoryUseCase
import com.example.umte_project.domain.usecase.expense.ExpenseUseCases
import com.example.umte_project.domain.usecase.category.GetCategoriesUseCase
import com.example.umte_project.domain.usecase.category.GetCategoryUseCase
import com.example.umte_project.domain.usecase.expense.AddExpenseUseCase
import com.example.umte_project.domain.usecase.expense.DeleteExpenseUseCase
import com.example.umte_project.domain.usecase.expense.GetExpenseUseCase
import com.example.umte_project.domain.usecase.expense.GetExpenseWithCategoryUseCase
import com.example.umte_project.domain.usecase.expense.GetExpensesUseCase
import com.example.umte_project.domain.usecase.expense.GetExpensesWithCategoryUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetExpensesUseCase(get()) }
    factory { GetExpenseUseCase(get()) }
    factory { AddExpenseUseCase(get()) }
    factory { DeleteExpenseUseCase(get()) }

    factory { GetExpensesWithCategoryUseCase(get()) }
    factory { GetExpenseWithCategoryUseCase(get()) }

    factory { GetCategoriesUseCase(get()) }
    factory { GetCategoryUseCase(get()) }
    factory { AddCategoryUseCase(get()) }
    factory { DeleteCategoryUseCase(get()) }

    factory {
        ExpenseUseCases(
            getExpenses = get(),
            getExpense = get(),
            addExpense = get(),
            deleteExpense = get(),
            getExpensesWithCategory = get(),
            getExpenseWithCategory = get()
        )
    }

    factory {
        CategoryUseCases(
            getCategories = get(),
            getCategory = get(),
            addCategory = get(),
            deleteCategory = get()
        )
    }
}