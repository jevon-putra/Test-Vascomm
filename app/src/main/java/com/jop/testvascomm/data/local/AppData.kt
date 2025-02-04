package com.jop.testvascomm.data.local

import android.content.Context

class AppData(context: Context) {
    private val PREFS_NAME = "TEST_VASCOMM_SHARED_PREF"
    private val TOKEN = "TOKEN"

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setToken(token: String) {
        prefs.edit().putString(TOKEN, token).apply()
    }

    fun getToken() = prefs.getString(TOKEN, "")
}