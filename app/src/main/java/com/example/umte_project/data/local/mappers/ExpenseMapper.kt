package com.example.umte_project.data.local.mappers

import com.example.umte_project.domain.models.Expense
import com.example.umte_project.data.local.entities.ExpenseEntity
import com.example.umte_project.data.local.entities.ExpenseWithCategoryEntity
import com.example.umte_project.domain.models.ExpenseWithCategory
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

fun ExpenseEntity.toDomain() = Expense(
    id = id,
    amount = amount,
    createdAt = Instant.ofEpochMilli(createdAtMillis).atZone(ZoneId.of(createdAtZoneId)),
    categoryId = categoryId,
    note = note,
    latitude = latitude,
    longitude = longitude,
    receiptImageUri = receiptImageUri
)

fun Expense.toEntity() = ExpenseEntity(
    id = id,
    amount = amount,
    createdAtMillis = createdAt.toInstant().toEpochMilli(),
    createdAtZoneId = createdAt.zone.id,
    categoryId = categoryId,
    note = note,
    latitude = latitude,
    longitude = longitude,
    receiptImageUri = receiptImageUri
)

fun ExpenseWithCategoryEntity.toDomain(): ExpenseWithCategory {
    return ExpenseWithCategory(
        expense = expenseEntity.toDomain(),
        category = categoryEntity.toDomain()
    )
}

fun ExpenseWithCategory.toEntity(): ExpenseWithCategoryEntity {
    return ExpenseWithCategoryEntity(
        expenseEntity = expense.toEntity(),
        categoryEntity = category.toEntity()
    )
}