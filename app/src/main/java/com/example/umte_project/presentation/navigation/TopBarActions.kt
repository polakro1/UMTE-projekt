package com.example.umte_project.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

class TopBarActions(
    val icon: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit
) {

}

fun getTopBarActionsForRoute(
    route: String?,
    navController: NavController,
): List<TopBarActions> {
    return when {
        route == null -> emptyList()

        route.startsWith(Routes.SELECT_CATEGORY) -> listOf(
            TopBarActions(
                icon = Icons.Default.Add,
                contentDescription = "Add category",
                onClick = { TODO() }
            )
        )

        else -> emptyList()
    }
}