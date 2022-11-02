package com.lazday.news.util

import android.content.Context
import android.content.SharedPreferences
import com.lazday.news.R

class SessionManager(context: Context) {

    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val ACCESS_TOKEN = "access_token"
    }

    fun saveAccessToken(token: String) {
        val editor = prefs.edit()
        editor.putString(ACCESS_TOKEN, token)
            .apply()
    }

    fun fetchAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN, null)
    }


    fun deleteAccessToken() {
        val editor = prefs.edit()
        editor.clear()
            .apply()
    }

}