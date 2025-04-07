package com.example.umte_project.di


import androidx.room.Room
import org.koin.dsl.module
import com.example.umte_project.data.local.ExpenseDatabase
import org.koin.android.ext.koin.androidContext


private const val DATABASE_NAME = "expense_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            ExpenseDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigrationFrom().build()
    }
}