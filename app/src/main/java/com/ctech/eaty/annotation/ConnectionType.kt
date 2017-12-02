package com.ctech.eaty.annotation

import android.net.ConnectivityManager
import android.support.annotation.IntDef

const val WIFI = ConnectivityManager.TYPE_WIFI.toLong()
const val MOBILE = ConnectivityManager.TYPE_MOBILE.toLong()
const val NONE = -1L

@Retention(AnnotationRetention.SOURCE)
@IntDef(WIFI, MOBILE, NONE)
annotation class ConnectionType