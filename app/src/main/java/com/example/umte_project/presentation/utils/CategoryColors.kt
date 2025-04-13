package com.example.umte_project.presentation.utils

data class CategoryColor(val name: String, val hex: String)


object CategoryColors {
    val all = listOf(
        CategoryColor("Tomato Red", "#F44336"),
        CategoryColor("Coral", "#FF7043"),
        CategoryColor("Sunflower Yellow", "#FFEB3B"),
        CategoryColor("Olive Green", "#689F38"),
        CategoryColor("Mint", "#4DB6AC"),
        CategoryColor("Sky Blue", "#29B6F6"),
        CategoryColor("Indigo", "#3F51B5"),
        CategoryColor("Plum", "#9C27B0"),
        CategoryColor("Stone Grey", "#757575"),
        CategoryColor("Orange", "#FF9800"),
        CategoryColor("Teal", "#00796B"),
        CategoryColor("Deep Blue", "#1E88E5"),
    )

    val default = all[3]
}