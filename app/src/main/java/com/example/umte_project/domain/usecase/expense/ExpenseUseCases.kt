package com.example.umte_project.domain.usecase.expense

class ExpenseUseCases(
    val getExpenses: GetExpenseUseCase,
    val getExpense: GetExpenseUseCase,
    val addExpense: AddExpenseUseCase,
    val deleteExpense: DeleteExpenseUseCase
) {
}