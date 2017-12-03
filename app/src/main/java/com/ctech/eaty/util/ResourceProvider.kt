package com.ctech.eaty.util

import android.support.annotation.StringRes
import java.io.File

interface ResourceProvider{
    fun getString(@StringRes id: Int): String
    fun getString(@StringRes resId: Int, vararg args: Any): String
    fun getCacheDir(): File
}