package com.ctech.eaty.repository

import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(val appSettingsManager: AppSettingsManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer ${
                appSettingsManager.run {
                    if(getUserToken().isEmpty()) getClientToken() else getUserToken()
                }}")
                .build()
        return chain.proceed(request)
    }
}