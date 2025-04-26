package com.example.umte_project.presentation.navigation

object Routes {
    const val EXPENSE_LIST = "expense_list"
    const val ADD_EXPENSE = "add_expense"
    const val EXPENSE_DETAIL = "expense_detail/{expenseId}"
    const val SELECT_CATEGORY = "select_category"
    const val ADD_CATEGORY = "add_category"
    const val EDIT_CATEGORY = "edit_category?categoryId={categoryId}"
    const val SELECT_LOCATION = "select_location"
    const val SETTINGS = "settings"


    fun expenseDetail(expenseId: Long): String {
        return "expense_detail/${expenseId}"
    }
}