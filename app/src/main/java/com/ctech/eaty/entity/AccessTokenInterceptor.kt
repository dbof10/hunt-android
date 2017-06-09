package com.ctech.eaty.entity

import com.ctech.eaty.repository.AppSettingsManager
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(val appSettingsManager: AppSettingsManager) : Interceptor {

    fun setSessionToken(token: AccessToken) {
        appSettingsManager.setToken(token.accessToken)
    }

    fun resetSessionToken() {
        appSettingsManager.resetToken()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer ${appSettingsManager.getToken()}")
                .build()
        return chain.proceed(request)
    }
}