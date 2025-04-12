package com.example.umte_project.domain.models

data class Category(
    val id: Long = 0,
    val name: String,
    val iconRes: String,
    val colorHex: String = "FFFFFF"
) {
}