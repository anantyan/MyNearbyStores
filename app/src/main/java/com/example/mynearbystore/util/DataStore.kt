package com.example.mynearbystore.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import javax.inject.Inject

const val DATASTORE_SETTINGS: String = "SETTINGS"
val LOGGED = booleanPreferencesKey("LOGGED")
val Context.dataStore by preferencesDataStore(DATASTORE_SETTINGS)

class DataStoreManager @Inject constructor(
    private val context: Context
) {
    fun setLogged(value: Boolean) = CoroutineScope(Dispatchers.IO).launch {
        context.dataStore.setValue(LOGGED, value)
    }

    fun getLogged() = runBlocking {
        context.dataStore.getValue(LOGGED, false).first()
    }
}

fun <T> DataStore<Preferences>.getValue(
    key: Preferences.Key<T>,
    defaultValue: T
): Flow<T> = this.data
    .catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[key] ?: defaultValue
    }

suspend fun <T> DataStore<Preferences>.setValue(key: Preferences.Key<T>, value: T) {
    this.edit { preferences ->
        preferences[key] = value
    }
}