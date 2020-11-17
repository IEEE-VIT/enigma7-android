package com.ieeevit.enigma7.utils

import android.content.Context
import android.content.SharedPreferences

class PrefManager(val context: Context) {
    private val PREFS_NAME = "com.ieeevit.enigma7"
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val authorizationCode: String = "AuthorizationCode"
    private val userNameExist: String = "userStatus"
    val hint:String="hintString"
    private val IS_FIRST_TIME_LAUNCH= "IsFirstTimeLaunch"
    private val gameStarted:String="IsGameStarted"
    private val xP ="xP"
    private val editor: SharedPreferences.Editor = sharedPref.edit()
     private val loggedIN="IsLoggedIn"


    fun setAuthCode(text: String) {
        editor.putString(authorizationCode, text)
        editor.apply()
    }

    fun setUserStatus(text: Boolean) {
        editor.putBoolean(userNameExist, text)
        editor.apply()
    }
    fun setFirstTimeInstruction(text: Boolean) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, text)
        editor.apply()
    }
    fun isFirstTimeInstruction(): Boolean {
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
        editor.putBoolean(loggedIN, text)
        editor.apply()
    }
    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean(loggedIN, false)
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

}