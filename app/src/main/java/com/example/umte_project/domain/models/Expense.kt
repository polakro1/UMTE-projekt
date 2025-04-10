package com.example.umte_project.domain.models

import java.time.ZonedDateTime

data class Expense(
    val id: Long = 0,
    val amount: Double,
    val createdAt: ZonedDateTime,
    val categoryId: Long,
    val note: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val receiptImageUri: String? = null
) {
}