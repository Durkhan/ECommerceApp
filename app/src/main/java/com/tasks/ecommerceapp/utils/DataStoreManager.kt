package com.tasks.ecommerceapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "prefs")

class DataStoreManager(private val dataStore: DataStore<Preferences>) {

    val token:Flow<String>
        get() = dataStore.data.map {
            it[token_]?:""
        }

    private companion object {
        val token_= stringPreferencesKey(name="token")
        val pin_= stringPreferencesKey(name="pin")
    }

    suspend fun saveToken(string: String){
        dataStore.edit {
            it[token_]=string
        }
    }
    val pin:Flow<String>
        get() = dataStore.data.map {
            it[pin_]?:""
        }


    suspend fun savePin(string: String){
        dataStore.edit {
            it[pin_]=string
        }
    }
}