package com.dicoding.bintangpr.clearvis.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dicoding.bintangpr.clearvis.data.model.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<Data> {
        return dataStore.data.map { preferences ->
            Data(
                preferences[ACCESSTOKEN_KEY] ?: ""
            )
        }
    }

    suspend fun saveUser(user: Data) {
        dataStore.edit { preferences ->
            preferences[ACCESSTOKEN_KEY] = user.accessToken
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val ACCESSTOKEN_KEY = stringPreferencesKey("accessToken")
    }
}