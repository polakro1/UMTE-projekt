package com.example.umte_project.presentation.add_edit_expense

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.umte_project.domain.models.Category
import com.example.umte_project.presentation.category_select.SelectCategoryScreen
import com.example.umte_project.presentation.navigation.Routes
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExpenseScreen(
    selectedCategoryId: Long? = null,
    navController: NavController,
    viewModel: AddEditExpenseViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    var categoryDropdownExpanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(state.createdAt.toInstant().toEpochMilli())
    val timePickerState = rememberTimePickerState(
        initialHour = state.createdAt.hour, initialMinute = state.createdAt.minute, is24Hour = true
    )
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showCategorySheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (selectedCategoryId != null) {
            viewModel.loadCategoryById(selectedCategoryId)
        }
    }

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

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        OutlinedTextField(
            value = state.amount.toString(),
            onValueChange = {
                try {
                    viewModel.onAmountChange(it.toDouble())
                } catch (e: Exception) {
                    //TODO handle error
                }
            },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(Routes.SELECT_CATEGORY) },
        ) {
            OutlinedTextField(
                value = state.selectedCategory?.name ?: "",
                onValueChange = {},
                label = { Text("Category") },
                readOnly = true,
            )
        }



        ExposedDropdownMenuBox(
            expanded = categoryDropdownExpanded,
            onExpandedChange = { categoryDropdownExpanded = it }) {
            OutlinedTextField(
                value = state.selectedCategory?.name ?: "",
                onValueChange = {},
                label = { Text("Category") },
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .clickable { categoryDropdownExpanded = true },
            )
            ExposedDropdownMenu(
                expanded = categoryDropdownExpanded,
                onDismissRequest = { categoryDropdownExpanded = false }) {
                state.categories.forEach { category ->
                    DropdownMenuItem(text = { Text(category.name) }, onClick = {
                        viewModel.onCategogySelected(category)
                        categoryDropdownExpanded = false
                    })
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = state.createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                onValueChange = {},
                label = { Text("Date and time") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {
                        showDatePicker = !showDatePicker
                        showTimePicker = false
                    }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select date"
                        )
                    }
                })
            OutlinedTextField(
                value = state.createdAt.format((DateTimeFormatter.ofPattern("HH:mm"))),
                onValueChange = {},
                label = { Text("Time") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {
                        showTimePicker = !showTimePicker
                        showDatePicker = false
                    }) {
                        Icon(
                            imageVector = Icons.Default.Schedule, contentDescription = "Select Time"
                        )
                    }
                })
        }
        if (showDatePicker) {
            DatePickerDialog(onDismissRequest = { showDatePicker = false }, confirmButton = {
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
            }, dismissButton = {
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
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { viewModel.saveExpense(onSave = {}) }) {
            if (state.isSaving) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Icon(imageVector = Icons.Default.Done, contentDescription = "save expense")
                Text("Save")
            }
        }

        Text(state.savingError ?: "no error")
    }
}

@ExperimentalMaterial3Api
@Composable
fun CategoryPickerSheet(
    selectedCategory: Category,
    onSelected: () -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    viewModel: AddEditExpenseViewModel = koinViewModel()
) {

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {

    }
}

@ExperimentalMaterial3Api
@Composable
fun DateTimePickerDialog(
    initialDateTime: ZonedDateTime, onDateSelected: (ZonedDateTime?) -> Unit, onDismiss: () -> Unit
) {
    var selectedDateTime by remember { mutableStateOf(initialDateTime) }

    val datePickerState = rememberDatePickerState(initialDateTime.toInstant().toEpochMilli())
    val timePickerState = rememberTimePickerState(
        initialHour = initialDateTime.hour, initialMinute = initialDateTime.minute, is24Hour = true
    )

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val selectedDate =
                Instant.ofEpochMilli(millis).atZone(initialDateTime.zone).toLocalDate()
            selectedDateTime = selectedDateTime.with(selectedDate)
        }
    }

    LaunchedEffect(timePickerState.hour, timePickerState.minute) {
        val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
        selectedDateTime = selectedDateTime.with(selectedTime)
    }

    AlertDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = {
            onDateSelected(selectedDateTime)
            onDismiss()
        }) { Text("OK") }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text("Cancel")
        }
    }, title = { Text("Select date and time") }, text = {
        Column {
            DatePicker(state = datePickerState)
            TimePicker(state = timePickerState)
        }
    })
}

@ExperimentalMaterial3Api
@Composable
fun DateTimePickerSection(
    initialDateTime: ZonedDateTime,
    datePickerState: DatePickerState,
    timePickerState: TimePickerState
) {
    var selectedDateTime by remember { mutableStateOf(initialDateTime) }
    var selectingTime by remember { mutableStateOf(false) }

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val selectedDate =
                Instant.ofEpochMilli(millis).atZone(initialDateTime.zone).toLocalDate()
            selectedDateTime = selectedDateTime.with(selectedDate)
        }
    }

    LaunchedEffect(timePickerState.hour, timePickerState.minute) {
        val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
        selectedDateTime = selectedDateTime.with(selectedTime)
    }
    Column {
        if (selectingTime) TimePicker(state = timePickerState)
        else DatePicker(state = datePickerState, showModeToggle = false)
        TextButton(onClick = { selectingTime = true }) {
            Row {
                Text(
                    text = selectedDateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}