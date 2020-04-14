package com.aupp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {

    companion object {
        private var sharedPreferences: SharedPreferences? = null

        private const val PREF_TIME = "PREF_TIME"

        @Volatile
        private var instance: SharedPreferencesHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildHelper(context).also {
                instance = it
            }
        }

        private fun buildHelper(context: Context): SharedPreferencesHelper {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

            return SharedPreferencesHelper()
        }
    }

    fun saveUpdateTime(time: Long) {
        sharedPreferences?.edit(commit = true) {
            putLong(PREF_TIME, time)
        }
    }

    fun getUpdateTime() = sharedPreferences?.getLong(PREF_TIME, 0)

    fun getCacheDuration() = sharedPreferences?.getString("pref_cache_duration", "")
}