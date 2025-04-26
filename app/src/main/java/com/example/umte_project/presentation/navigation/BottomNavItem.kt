package com.example.umte_project.presentation.navigations

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.umte_project.R
import com.example.umte_project.presentation.navigation.Routes

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Dashboard : BottomNavItem(Routes.DASHBOARD, "Dashboard", Icons.Filled.Dashboard)
    object Expenses : BottomNavItem(Routes.EXPENSE_LIST, "Expenses", Icons.Filled.List)
    object Settings : BottomNavItem(Routes.SETTINGS, "Settings", Icons.Filled.Settings)
    object Statistics : BottomNavItem("", "Statistics", Icons.Filled.BarChart)
}