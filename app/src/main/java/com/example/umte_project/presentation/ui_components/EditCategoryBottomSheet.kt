package com.example.umte_project.presentation.ui_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoryBottomSheet(
    onDismiss: () -> Unit,
    onChangeColor: () -> Unit,
    onChangeIcon: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = Modifier.padding(15.dp)
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            SheetOptionItem(
                icon = Icons.Default.ImageSearch,
                text = "Change icon",
                onClick = {
                    onDismiss()
                    onChangeIcon()
                }
            )

            SheetOptionItem(
                icon = Icons.Default.ColorLens,
                text = "Change color",
                onClick = {
                    onDismiss()
                    onChangeColor()
                }
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            SheetOptionItem(
                icon = Icons.Default.Close,
                text = "Cancel",
                onClick = { onDismiss() }
            )
        }
    }
}