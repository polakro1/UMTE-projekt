package com.example.umte_project.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val iconRes: Int?, // odkaz na res?
    val colorHex: String = "#FFFFFF"
) {
}