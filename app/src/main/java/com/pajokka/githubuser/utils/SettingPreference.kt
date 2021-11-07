package com.pajokka.githubuser.utils

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreference(application: Application) {
    private val THEME_KEY = booleanPreferencesKey("theme_setting")
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val dataStoreTheme = application.dataStore

    fun getThemeSetting(): Flow<Boolean> = dataStoreTheme.data.map {
        it[THEME_KEY] ?: false
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStoreTheme.edit {
            it[THEME_KEY] = isDarkModeActive
        }
    }
}