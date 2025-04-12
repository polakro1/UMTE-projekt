package com.example.umte_project.presentation.ui_components

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home

import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class CategoryIcons(
    val iconName: String,
    val icon: ImageVector
) {
    FOOD("Food", Icons.Default.Restaurant),
    SHOPPING("Shopping", Icons.Default.ShoppingCart),
    TRANSPORT("Travel", Icons.Default.DirectionsCar),
    HOME("Home", Icons.Default.Home),
    OTHER("Other", Icons.Default.Menu);

    companion object {
        fun fromName(name: String): CategoryIcons {
            Log.d("Test", name)
            return entries.firstOrNull { it.iconName == name } ?: OTHER
        }
    }
}