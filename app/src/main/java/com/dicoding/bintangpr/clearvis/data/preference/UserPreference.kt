package com.dicoding.bintangpr.clearvis.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dicoding.bintangpr.clearvis.data.model.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<Data> {
        return dataStore.data.map { preferences ->
            Data(
                preferences[NAME_KEY] ?:"",
                preferences[ACCESSTOKEN_KEY] ?:""
            )
        }
    }

    suspend fun saveUser(user: Data) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.name
            preferences[ACCESSTOKEN_KEY] = user.accessToken
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = ""
            preferences[ACCESSTOKEN_KEY] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val ACCESSTOKEN_KEY = stringPreferencesKey("accessToken")


        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}