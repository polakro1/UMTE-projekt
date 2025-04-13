package com.example.umte_project.presentation.add_edit_category


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.umte_project.presentation.ui_components.CategoryIcon
import com.example.umte_project.presentation.ui_components.CategoryIconVariant
import com.example.umte_project.presentation.ui_components.ColorPickerBottomSheet
import com.example.umte_project.presentation.ui_components.EditCategoryBottomSheet
import com.example.umte_project.presentation.ui_components.IconPickerBottomSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddEditCategoryScreen(
    navController: NavController,
    categoryId: Long? = null,
    viewModel: AddEditCategoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showEditSheet by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }
    var showIconPicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (categoryId == null) viewModel.initNewCategory()
        else viewModel.getCategory(categoryId)
    }

    when (val currentState = state) {
        is AddEditCategoryState.Loading -> CircularProgressIndicator()
        is AddEditCategoryState.Error -> Text(currentState.message)
        is AddEditCategoryState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CategoryIcon(
                        currentState.selectedIcon,
                        currentState.selectedColor,
                        CategoryIconVariant.Large,
                        onEditClick = { showEditSheet = true })
                    Spacer(modifier = Modifier.height(32.dp))
                    OutlinedTextField(
                        value = currentState.name,
                        onValueChange = { viewModel.onNameChange(it) },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Button(
                    onClick = ({ viewModel.saveCategory(onSave = { navController.popBackStack() }) }),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }
            }



            if (showEditSheet) {
                EditCategoryBottomSheet(
                    onDismiss = { showEditSheet = false },
                    onChangeColor = { showColorPicker = true },
                    onChangeIcon = { showIconPicker = true })
            }

            if (showColorPicker) {
                ColorPickerBottomSheet(
                    selectedColor = currentState.selectedColor,
                    onColorSelected = { viewModel.onColorChange(it) },
                    onDismiss = { showColorPicker = false })
            }

            if (showIconPicker) {
                IconPickerBottomSheet(
                    currentIconName = currentState.selectedIcon,
                    onIconSelected = { viewModel.onIconChange(it) },
                    onDismiss = { showIconPicker = false })
            }
        }

        is AddEditCategoryState.Saved -> {
        }
    }
}




