package com.ctech.eaty.util

import android.support.annotation.StringRes

interface ResourceProvider{
    fun getString(@StringRes id: Int): String
}