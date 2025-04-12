package com.example.umte_project.domain.usecase.expense

class ExpenseUseCases(
    val getExpenses: GetExpensesUseCase,
    val getExpense: GetExpenseUseCase,
    val addExpense: AddExpenseUseCase,
    val deleteExpense: DeleteExpenseUseCase,
    val getExpensesWithCategory: GetExpensesWithCategoryUseCase,
    val getExpenseWithCategory: GetExpenseWithCategoryUseCase
) {
}