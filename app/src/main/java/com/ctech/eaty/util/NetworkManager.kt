package com.ctech.eaty.util

import android.content.Context
import android.net.ConnectivityManager


interface NetworkManager {
    fun isConnected(): Boolean

    class IMPL(private val context: Context) : NetworkManager {
        override fun isConnected(): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }

    }
}