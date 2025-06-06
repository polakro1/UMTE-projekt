package com.example.umte_project.di

import com.example.umte_project.presentation.add_edit_category.AddEditCategoryViewModel
import com.example.umte_project.presentation.add_edit_expense.AddEditExpenseViewModel
import com.example.umte_project.presentation.category_select.SelectCategoryViewModel
import com.example.umte_project.presentation.dashboard.DashboardViewModel
import org.koin.core.module.dsl.viewModel
import com.example.umte_project.presentation.expense_list.ExpenseListViewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { ExpenseListViewModel(get()) }
    viewModel { AddEditExpenseViewModel(get()) }
    viewModel { SelectCategoryViewModel(get()) }
    viewModel { AddEditCategoryViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
}