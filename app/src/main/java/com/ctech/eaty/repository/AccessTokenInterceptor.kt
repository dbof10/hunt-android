package com.ctech.eaty.repository

import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(val appSettingsManager: AppSettingsManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader("User-Agent", "ProductHunt/3.8.9 (iPad; iOS 10.3.3; Scale/2.00)")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer ${
                appSettingsManager.run {
                    if(getUserToken().isEmpty()) getClientToken() else getUserToken()
                }}")
                .build()
        return chain.proceed(request)
    }
}