package com.example.umte_project.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

class ExpenseWithCategoryEntity(
    @Embedded val expenseEntity: ExpenseEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val categoryEntity: CategoryEntity
) {
}