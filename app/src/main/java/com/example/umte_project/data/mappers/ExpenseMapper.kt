package com.example.umte_project.data.mappers

import com.example.umte_project.domain.models.Expense
import com.example.umte_project.data.local.entities.ExpenseEntity
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

fun ExpenseEntity.toDomain() = Expense(
    id = id,
    title = title,
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
    title = title,
    amount = amount,
    createdAtMillis = createdAt.toInstant().toEpochMilli(),
    createdAtZoneId = createdAt.zone.id,
    categoryId = categoryId,
    note = note,
    latitude = latitude,
    longitude = longitude,
    receiptImageUri = receiptImageUri
)