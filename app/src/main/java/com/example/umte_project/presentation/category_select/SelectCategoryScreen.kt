package com.example.umte_project.presentation.category_select

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
    LazyColumn {
        items(state.categories) { category ->
            CategoryListItem(
                category = category,
                onClick = {
                    viewModel.onCategorySelected(category, onSave = {
                        navController.navigate(Routes.addExpenseWithCategory(category.id)) {
                            popUpTo(Routes.ADD_EXPENSE) {
                                inclusive = true
                            }
                        }
                    })
                })
        }
    }
}