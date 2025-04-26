package com.example.umte_project.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension pro Context
val Context.dataStore by preferencesDataStore(name = "settings")

object PreferenceManager {

    private val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")

    suspend fun setBiometricEnabled(context: Context, enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[BIOMETRIC_ENABLED] = enabled
        }
    }

    fun getBiometricEnabled(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[BIOMETRIC_ENABLED] ?: false
        }
    }
}