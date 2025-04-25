package com.example.umte_project.presentation.navigation

object NavigationUtils {

    fun getTitleForRoute(route: String?): String {
        return when {
            route == null -> ""
            route.startsWith(Routes.EXPENSE_LIST) -> "Expenses"
            route.startsWith(Routes.ADD_EXPENSE) -> "Add Expense"
            route.startsWith(Routes.EXPENSE_DETAIL) -> "Expense Detail"
            route.startsWith(Routes.SELECT_CATEGORY) -> "Select Category"
            route.startsWith(Routes.ADD_CATEGORY) -> "Add Category"
            route.startsWith(Routes.SELECT_LOCATION) -> "Select Location"
            else -> ""
        }
    }

    fun shouldShowBottomBar(route: String?): Boolean {
        return when {
            route == null -> false
            route.startsWith(Routes.EXPENSE_LIST) -> true
            else -> false
        }
    }

    fun shouldShowBackButton(currentRoute: String?): Boolean {
        return when (currentRoute) {
            Routes.EXPENSE_LIST,
                -> false

            else -> true
        }
    }
}