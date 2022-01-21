package com.example.minimarket.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit {
            putInt(key, value)
        }
    }

    fun getInt(key: String, defaultValue: Int) = sharedPreferences.getInt(key, defaultValue)

    companion object {
        const val LAYOUT_TYPE = "LAYOUT_TYPE"
    }

}