package com.example.umte_project.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val createdAtMillis: Long,
    val createdAtZoneId: String,
    val categoryId: Long,
    val note: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val receiptImageUri: String? = null
) {

}