package com.example.umte_project.presentation.navigation

object Routes {
    const val EXPENSE_LIST = "expense_list"
    const val ADD_EDIT_EXPENSE = "add_edit_expense"
    const val EXPENSE_DETAIL = "expense_detail/{expenseId}"

    fun expenseDetail(expenseId: Long): String {
        return "expense_detail/${expenseId}"
    }
}