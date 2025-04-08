package com.example.umte_project.domain.models

data class Category(
    val id: Long = 0,
    val name: String,
    val iconRes: Int?,
    val colorHex: String = "FFFFFF"
) {
}