package com.ctech.eaty.repository

import android.content.SharedPreferences
import com.ctech.eaty.entity.UserDetail
import com.google.gson.Gson

class AppSettingsManager(private val sharedPreferences: SharedPreferences, private val gson: Gson) {

    private val CLIENT_ACCESS_TOKEN_KEY = "clientAccessToken"
    private val USER_ACCESS_TOKEN_KEY = "userAccessToken"
    private val USER_KEY = "user"
    private val KEY_BOARDING = "onboarding"

    fun setClientToken(token: String) {
        sharedPreferences.edit().putString(CLIENT_ACCESS_TOKEN_KEY, token).apply()
    }

    fun getClientToken(): String = sharedPreferences.getString(CLIENT_ACCESS_TOKEN_KEY, "")

    fun resetClientToken() {
        sharedPreferences.edit().remove(CLIENT_ACCESS_TOKEN_KEY).apply()
    }

    fun setUserToken(token: String) {
        sharedPreferences.edit().putString(USER_ACCESS_TOKEN_KEY, token).apply()
    }

    fun getUserToken(): String = sharedPreferences.getString(USER_ACCESS_TOKEN_KEY, "")

    fun resetUserToken() {
        sharedPreferences.edit().remove(USER_ACCESS_TOKEN_KEY).apply()
    }

    fun storeUser(user: UserDetail) {
        sharedPreferences.edit().putString(USER_KEY, gson.toJson(user)).apply()
    }

    fun getUser(): UserDetail {
        val persistedUser = sharedPreferences.getString(USER_KEY, "")
        if (persistedUser.isEmpty()) {
            return UserDetail.GUEST
        }
        return gson.fromJson(persistedUser, UserDetail::class.java)
    }

    fun setDidSeeOnboarding() {
        sharedPreferences.edit().putBoolean(KEY_BOARDING, true).apply()
    }

    fun didSeeOnboarding() = sharedPreferences.getBoolean(KEY_BOARDING, false)


}