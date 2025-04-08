package com.example.umte_project.di

import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.umte_project.presentation.expense_list.ExpenseListViewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ExpenseListViewModel(get()) }
}