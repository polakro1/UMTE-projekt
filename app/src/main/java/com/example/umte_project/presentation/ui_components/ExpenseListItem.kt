package com.example.umte_project.presentation.ui_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.umte_project.domain.models.ExpenseWithCategory
import java.time.format.DateTimeFormatter

@Composable
fun ExpenseListItem(
    modifier: Modifier = Modifier,
    expense: ExpenseWithCategory,
    onClick: (ExpenseWithCategory) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(expense) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryIcon(
            iconName = expense.category.name,
            colorHex = expense.category.colorHex,
            variant = CategoryIconVariant.Medium
        )

        Spacer(modifier = modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
            Text(
                text = expense.category.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = expense.expense.note ?: "",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = expense.expense.amount.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = if (expense.expense.amount < 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
            Text(
                text = expense.expense.createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }

    }
}