package com.pajokka.githubuser.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences(val context: Context) {
    companion object {
        const val USERNAME_PREF = "USERNAME_PREF"
    }

    private val sharedPref: SharedPreferences = context.getSharedPreferences(USERNAME_PREF, Context.MODE_PRIVATE)

    fun setValues(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValues(key: String): String? {
        return sharedPref.getString(key, null)
    }

}