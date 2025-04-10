package com.example.umte_project.domain.utils

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Succes<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}