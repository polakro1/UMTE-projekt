package com.example.umte_project.di


import androidx.room.Room
import org.koin.dsl.module
import com.example.umte_project.data.local.ExpenseDatabase
import com.example.umte_project.data.repository.CategoryRepositoryImpl
import com.example.umte_project.data.repository.ExpenseRepositoryImpl
import com.example.umte_project.domain.repository.CategoryRepository
import com.example.umte_project.domain.repository.ExpenseRepository
import org.koin.android.ext.koin.androidContext


private const val DATABASE_NAME = "expense_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(), ExpenseDatabase::class.java, DATABASE_NAME
        ).createFromAsset("database/expense_db.db")
            .fallbackToDestructiveMigration()// jen pro testování
            .build()
    }

    single { get<ExpenseDatabase>().expenseDao() }
    single { get<ExpenseDatabase>().categoryDao() }

    single<ExpenseRepository> {
        ExpenseRepositoryImpl(get())
    }
    single<CategoryRepository> {
        CategoryRepositoryImpl(get())
    }
}