package com.example.umte_project.presentation.category_select

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.umte_project.presentation.navigation.Routes
import com.example.umte_project.presentation.ui_components.CategoryIcon
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
    Column {
        Row {
            LazyColumn {
                items(state.categories) { category ->
                    Row(
                        modifier = Modifier.clickable { viewModel.onCategogySelected(category) }
                    ) {
                        CategoryIcon(category.iconRes)
                        Text(category.name)
                        if (state.selectedCategory?.id == category.id) {
                            Text("Selected")
                        }
                    }
                }
            }


        }
        Button(
            enabled = state.selectedCategory != null,
            onClick = {
                navController.navigate(Routes.addEditExpenseWithCategory(state.selectedCategory!!.id)) {
                    popUpTo(Routes.ADD_EXPENSE) {
                        inclusive = true
                    }
                }
            }
        )
        { Text("Save") }
    }


}