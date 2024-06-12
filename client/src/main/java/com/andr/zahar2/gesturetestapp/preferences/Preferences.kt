package com.andr.zahar2.gesturetestapp.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

object Preferences {

    private val Context.dataStore by preferencesDataStore(name = "settings")

    private val IP = stringPreferencesKey("ip")
    private val PORT = intPreferencesKey("port")

    val Context.ipFlow: Flow<String?>
        get() = get(IP) { it }

    fun Context.setIp(ip: String) =
        set(IP, ip)

    val Context.portFlow: Flow<Int?>
        get() = get(PORT) { it }

    fun Context.setPort(port: Int) =
        set(PORT, port)

    fun <F, T> Context.get(key: Preferences.Key<F>, map: (F?) -> T?): Flow<T?> =
        dataStore.data.map {
            map(it[key])
        }

    fun <T> Context.set(key: Preferences.Key<T>, value: T) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit {
                it[key] = value
            }
        }
    }
}