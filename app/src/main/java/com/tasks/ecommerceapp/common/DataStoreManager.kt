package com.tasks.ecommerceapp.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "prefs")

@Singleton
class DataStoreManager@Inject constructor(@ApplicationContext private val context: Context) {
    private val dataStore: DataStore<Preferences> = context.datastore
    val token:Flow<String>
        get() = dataStore.data.map {
            it[token_]?:""
        }

    private companion object {
        val token_= stringPreferencesKey(name="token")
        val pin_= stringPreferencesKey(name="pin")
        val rememberMe= booleanPreferencesKey(name = "rememberMe_")
        val firstTime= booleanPreferencesKey(name = "firstTime_")

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

    suspend fun setRemember(boolean: Boolean){
        dataStore.edit {
            it[rememberMe]=boolean
        }
    }

    val isRemember:Flow<Boolean>
        get()=dataStore.data.map {
            it[rememberMe]?:false
        }

    suspend fun setFirstTime(boolean: Boolean){
        dataStore.edit {
            it[firstTime]=boolean
        }
    }

    val isFirstTime:Flow<Boolean>
        get()=dataStore.data.map {
            it[firstTime]?:true
        }


}