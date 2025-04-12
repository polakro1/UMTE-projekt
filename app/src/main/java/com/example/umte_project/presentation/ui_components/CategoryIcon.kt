package com.example.umte_project.presentation.ui_components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun CategoryIcon(iconName: String) {
    val icon = CategoryIcons.fromName(iconName)

    Icon(imageVector = icon.icon, contentDescription = icon.iconName)
}