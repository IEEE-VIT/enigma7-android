package com.ieeevit.enigma7.utils

import android.content.Context
import android.content.SharedPreferences

class PrefManager(val context: Context) {
    private val PREFS_NAME = "com.ieeevit.enigma7"
    val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val authorizationCode: String = "AuthorizationCode"
    val userNameExist: String = "userStatus"

    fun save(KEY_NAME: String, text: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor.apply()
    }

    fun setAuthCode(text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(authorizationCode, text)
        editor.apply()
    }

    fun setUserStatus(text: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(userNameExist, text)
        editor.apply()
    }

    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getAuthCode(): String? {
        return sharedPref.getString(authorizationCode, null)
    }

    fun getUserStaus(): Boolean? {
        return sharedPref.getBoolean(userNameExist, false)
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor.clear()
        editor.apply()
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }
}