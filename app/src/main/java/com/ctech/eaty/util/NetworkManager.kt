package com.ctech.eaty.util

import android.content.Context
import android.net.ConnectivityManager
import com.ctech.eaty.annotation.ConnectionType
import com.ctech.eaty.annotation.MOBILE
import com.ctech.eaty.annotation.NONE
import com.ctech.eaty.annotation.WIFI


interface NetworkManager {
    fun isConnected(): Boolean

    @ConnectionType
    fun connectionType(): Long

    class IMPL(private val context: Context) : NetworkManager {
        private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        override fun connectionType(): Long {
            return if (isConnected()) {
                if (connectivityManager.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                    WIFI
                } else {
                    MOBILE
                }
            } else {
                NONE
            }
        }

        override fun isConnected(): Boolean {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }

    }
}