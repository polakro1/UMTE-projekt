package com.example.umte_project

import android.app.Application
import com.example.umte_project.di.databaseModule
import com.example.umte_project.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(databaseModule, useCaseModule)
        }
    }
}