package com.ctech.eaty.react

import com.ctech.eaty.repository.AppSettingsManager
import com.facebook.react.bridge.*


class AppSettingsModule(context: ReactApplicationContext, private val appSettings: AppSettingsManager) : ReactContextBaseJavaModule(context) {

    override fun getName() = "AppSettings"

    @ReactMethod
    fun getClientToken(promise: Promise) {
        val map = Arguments.createMap()
        map.putString("token", appSettings.getClientToken())
        promise.resolve(map)
    }

    @ReactMethod
    fun getUserToken(promise: Promise) {
        val map = Arguments.createMap()
        map.putString("token", appSettings.getUserToken())
        promise.resolve(map)
    }
}