package com.example.umte_project.presentation.category_select

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.umte_project.presentation.navigation.Routes
import com.example.umte_project.presentation.ui_components.CategoryListItem
import org.koin.androidx.compose.koinViewModel


@Composable
fun SelectCategoryScreen(
    navController: NavController,
    viewModel: SelectCategoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadCategories()
    }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(state.categories) { category ->
            CategoryListItem(
                category = category,
                onClick = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selected_category", category)
                    navController.popBackStack()
                })
        }
    }
}