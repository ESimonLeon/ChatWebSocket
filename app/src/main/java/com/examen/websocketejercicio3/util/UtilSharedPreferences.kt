package com.examen.websocketejercicio3.util

import android.content.Context
import com.examen.websocketejercicio3.R

class UtilSharedPreferences(var context: Context) {

    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.app_name), Context.MODE_PRIVATE
    )

    fun getSharedPreferences(namePreferences: String, default: String = ""): String {
        return sharedPref.getString(namePreferences, default).toString()
    }

    fun getSharedPreferences(namePreferences: String, default: Boolean = false): Boolean {
        return sharedPref.getBoolean(namePreferences, default)
    }

    fun getSharedPreferences(namePreferences: String, default: Int = 0): Int {
        return sharedPref.getInt(namePreferences, default)
    }

    fun saveSharedPreferences(namePreferences: String, data: String) {
        val editor = sharedPref.edit()
        editor.putString(namePreferences, data)
        editor.apply()
    }

    fun saveSharedPreferences(namePreferences: String, data: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean(namePreferences, data)
        editor.apply()
    }

    fun saveSharedPreferences(namePreferences: String, data: Int) {
        val editor = sharedPref.edit()
        editor.putInt(namePreferences, data)
        editor.apply()
    }

    fun dropSharedPreferencesText(namePreferences: String) {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.app_name), Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.remove(namePreferences)
        editor.apply()
    }
}