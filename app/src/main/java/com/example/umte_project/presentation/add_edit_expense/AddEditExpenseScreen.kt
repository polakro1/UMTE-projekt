package com.example.umte_project.presentation.add_edit_expense

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.umte_project.presentation.navigation.Routes
import com.example.umte_project.presentation.ui_components.AmountInput
import com.example.umte_project.presentation.ui_components.CategoryListItem
import com.example.umte_project.presentation.ui_components.ConfirmButton
import com.example.umte_project.presentation.ui_components.SelectableFieldItem
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExpenseScreen(
    selectedCategoryId: Long? = null,
    expenseId: Long? = null,
    navController: NavController,
    viewModel: AddEditExpenseViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (selectedCategoryId != null) {
            viewModel.loadCategoryById(selectedCategoryId)
        } else if (expenseId != null) {
            viewModel.getExpense(expenseId)
        } else {
            viewModel.initNewExpense()
        }
    }

    when (val state = state) {
        is AddEditExpenseState.Loading -> CircularProgressIndicator()
        is AddEditExpenseState.Error -> Text(state.message)
        is AddEditExpenseState.Saved -> {}
        is AddEditExpenseState.Success -> {
            val scrollState = rememberScrollState()
            var showDatePicker by remember { mutableStateOf(false) }
            var showTimePicker by remember { mutableStateOf(false) }
            val datePickerState =
                rememberDatePickerState(state.createdAt.toInstant().toEpochMilli())
            val timePickerState = rememberTimePickerState(
                initialHour = state.createdAt.hour,
                initialMinute = state.createdAt.minute,
                is24Hour = true
            )

            LaunchedEffect(datePickerState.selectedDateMillis) {
                datePickerState.selectedDateMillis?.let { millis ->
                    val selectedDate =
                        Instant.ofEpochMilli(millis).atZone(state.createdAt.zone).toLocalDate()

                    viewModel.onCreatedAtChange(state.createdAt.with(selectedDate))
                }
            }

            LaunchedEffect(timePickerState.hour, timePickerState.minute) {
                val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                viewModel.onCreatedAtChange(state.createdAt.with(selectedTime))
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AmountInput(
                    amountText = state.amount,
                    onAmountChange = { viewModel.onAmountChange(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                when (state.selectedCategory) {
                    null -> {
                        SelectableFieldItem(
                            label = "Category",
                            icon = Icons.Default.QuestionMark,
                            color = Color.Gray,
                            onClick = { navController.navigate(Routes.SELECT_CATEGORY) },
                            trailingText = "Required",
                            contentDescription = "Select category"
                        )
                    }

                    else -> {
                        CategoryListItem(
                            category = state.selectedCategory,
                            onClick = { navController.navigate(Routes.SELECT_CATEGORY) },
                            trailingContent = {
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = "Select category"
                                )
                            })
                    }
                }

                HorizontalDivider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            .clickable {
                                showDatePicker = !showDatePicker
                                showTimePicker = false
                            }
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Today,
                            contentDescription = "Select Time",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = state.createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            .clickable {
                                showTimePicker = !showTimePicker
                                showDatePicker = false
                            }
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Select Time",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = state.createdAt.format(DateTimeFormatter.ofPattern("HH:mm")))
                    }
                }
                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = {
                                viewModel.onCreatedAtChange(
                                    state.createdAt.with(
                                        Instant.ofEpochMilli(
                                            datePickerState.selectedDateMillis!!
                                        ).atZone(ZoneId.systemDefault()).toLocalDate()
                                    )
                                )
                                showDatePicker = false
                            }) { Text("OK") }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false }) {
                                Text("Cancel")
                            }
                        }) {
                        DatePicker(state = datePickerState)
                    }
                }
                if (showTimePicker) {
                    AlertDialog(onDismissRequest = { showTimePicker = false }, dismissButton = {
                        TextButton(onClick = { showTimePicker = false }) {
                            Text("Cancel")
                        }
                    }, confirmButton = {
                        TextButton(onClick = {
                            viewModel.onCreatedAtChange(
                                state.createdAt.with(
                                    LocalTime.of(timePickerState.hour, timePickerState.minute)
                                )
                            )
                            showTimePicker = false
                        }) { Text("OK") }
                    }, text = {
                        TimePicker(timePickerState)
                    })
                }

                OutlinedTextField(
                    value = state.note,
                    onValueChange = { viewModel.onNoteChanged(it) },
                    label = { Text("Note") },
                    singleLine = false,
                    maxLines = 5,
                    keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp)
                        .height(200.dp)
                )


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ConfirmButton(
                        text = "Save",
                        onClick = { viewModel.saveExpense { navController.popBackStack() } },
                        isLoading = state.isSaving,
                        enabled = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                }
            }
        }
    }
}