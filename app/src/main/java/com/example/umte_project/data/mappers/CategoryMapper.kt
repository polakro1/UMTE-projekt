package com.example.umte_project.data.mappers

import com.example.umte_project.data.local.entities.CategoryEntity
import com.example.umte_project.domain.models.Category

fun CategoryEntity.toDomain() = Category(
    id = id,
    name = name,
    iconRes = iconRes,
    colorHex = colorHex
)

fun Category.toEntity() = CategoryEntity(
    id = id,
    name = name,
    iconRes = iconRes,
    colorHex = colorHex
)