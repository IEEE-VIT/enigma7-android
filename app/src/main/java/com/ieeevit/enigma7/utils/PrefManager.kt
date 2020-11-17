package com.ieeevit.enigma7.utils

import android.content.Context
import android.content.SharedPreferences

class PrefManager(val context: Context) {
    private val PREFS_NAME = "com.ieeevit.enigma7"
    val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val authorizationCode: String = "AuthorizationCode"
    val userNameExist: String = "userStatus"
    val hint:String="hintString"
    private val IS_FIRST_TIME_LAUNCH= "IsFirstTimeLaunch"
    val gameStarted:String="IsGameStarted"
    val xP ="xP"
    val editor: SharedPreferences.Editor = sharedPref.edit()

    fun save(KEY_NAME: String, text: String) {
        editor.putString(KEY_NAME, text)
        editor.apply()
    }

    fun setAuthCode(text: String) {
        editor.putString(authorizationCode, text)
        editor.apply()
    }

    fun setUserStatus(text: Boolean) {
        editor.putBoolean(userNameExist, text)
        editor.apply()
    }
    fun setFirstTimeLaunch(text: Boolean) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, text)
        editor.apply()
    }
    fun isFirstTimeLaunch(): Boolean {
        return sharedPref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
    }

    fun setHuntStarted(text: Boolean) {
        editor.putBoolean(gameStarted, text)
        editor.apply()
    }
    fun isHuntStarted(): Boolean {
        return sharedPref.getBoolean(gameStarted, false)
    }
    fun setIsLoggedIn(text: Boolean) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, text)
        editor.apply()
    }
    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean(IS_FIRST_TIME_LAUNCH, false)
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
    fun setHint(text:String?) {
        editor.putString(hint, text)
        editor.apply()
    }
    fun getHintString(): String? {
        return sharedPref.getString(hint, null)
    }
    fun clearSharedPreference() {
        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor.clear()
        editor.apply()
    }
    fun setXp(text:Int) {
        editor.putInt(xP, text)
        editor.apply()
    }
    fun getXp(): Int {
        return sharedPref.getInt(xP,0)
    }
    fun removeValue(KEY_NAME: String) {
        editor.remove(KEY_NAME)
        editor.apply()
    }
}