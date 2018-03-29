package com.ctech.eaty.annotation

import android.net.ConnectivityManager
import android.support.annotation.IntDef

const val WIFI = ConnectivityManager.TYPE_WIFI
const val MOBILE = ConnectivityManager.TYPE_MOBILE
const val NONE = -1

@Retention(AnnotationRetention.SOURCE)
@IntDef(WIFI, MOBILE, NONE)
annotation class ConnectionType