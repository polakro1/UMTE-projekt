package com.example.umte_project.presentation.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class CategoryIconVariant {
    Small, Medium, Large
}

@Composable
fun CategoryIcon(
    iconName: String,
    colorHex: String,
    variant: CategoryIconVariant,
    onEditClick: (() -> Unit)? = null
) {
    val icon = CategoryIcons.fromName(iconName)

    val (iconSize, backgroundSize) = when (variant) {
        CategoryIconVariant.Small -> 26.dp to 36.dp
        CategoryIconVariant.Medium -> 28.dp to 48.dp
        CategoryIconVariant.Large -> 100.dp to 150.dp
    }
    val backgroundColor = remember(colorHex) {
        try {
            Color(android.graphics.Color.parseColor(colorHex))
        } catch (e: Exception) {
            Color.Gray
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(backgroundSize))
    {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape)
                .background(backgroundColor)
        ) {
            Icon(
                modifier = Modifier
                    .size(iconSize),
                imageVector = icon.icon,
                contentDescription = icon.iconName
            )
        }

        if (onEditClick != null) {
            IconButton(
                onClick = onEditClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(Color.White, shape = CircleShape)
                    .border(1.dp, Color.Gray, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}