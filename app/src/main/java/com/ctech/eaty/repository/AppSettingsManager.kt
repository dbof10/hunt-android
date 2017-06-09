package com.ctech.eaty.repository

import android.content.SharedPreferences

class AppSettingsManager(private val sharedPreferences: SharedPreferences) {

    private val ACCESS_TOKEN_KEY = "accessToken"

    fun setToken(token: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }

    fun getToken(): String = sharedPreferences.getString(ACCESS_TOKEN_KEY, "")

    fun resetToken() {
        sharedPreferences.edit().remove(ACCESS_TOKEN_KEY).apply()
    }
}